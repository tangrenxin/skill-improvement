package com.b.project.b.networkflowAnalysis.a.hotPagesNetWorkFlow

import java.sql.Timestamp
import java.text.SimpleDateFormat

import org.apache.flink.api.common.functions.AggregateFunction
import org.apache.flink.api.common.state.{ListState, ListStateDescriptor}
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

import scala.collection.mutable.ListBuffer

/**
 * @Description:
 * 实时热门页面流量统计
 * 基本实现
 * @Author: tangrenxin
 * @Date: 2021/11/20 17:38
 */

object Code01HotPagesNetworkFlow {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    // 设置时间语义，使用日志里的时间
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

    // 读取数据，转换成样例类并提取时间戳和watermark
        val inputStream = env.readTextFile("/Users/tangrenxin/xiaomiADProject/skill-improvement/flink-module/src/main/resources/apache.log")

    val dataStream = inputStream
      .map(data => {
        val arr = data.split(" ")
        // 对事件时间进行转换，得到时间戳
        val simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss")
        val ts = simpleDateFormat.parse(arr(3)).getTime
        ApacheLogEvent(arr(0), arr(1), ts, arr(5), arr(6))
      })
      // 日志中的数据是乱序数据，提取时间戳和watermark(最大乱序程度)
      .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[ApacheLogEvent](Time.minutes(1)) {
        override def extractTimestamp(element: ApacheLogEvent): Long = {
          element.timestamp
        }
      })

    // 进行开窗聚合，以及排序输出
    val aggStream: DataStream[PageViewCount] = dataStream
      .filter(_.method == "GET")
      // 过滤掉静态页面等
      .filter( data => { val pattern = "^((?!\\.(css|js)$).)*$".r
        (pattern findFirstIn data.url).nonEmpty } )
      .keyBy(_.url)
      // 开窗，每5秒输出最近十分钟的热门页面
      .timeWindow(Time.minutes(10), Time.seconds(5))
      // 聚合函数：先做一个预聚合，后面再做合并window之后包装成样例类的操作
      .aggregate(new PageCountAgg(), new PageViewCountWindowResult())

    // 按照时间窗口聚合，计算出窗口内的热门页面并排序取topN
    val resultStream: DataStream[String] = aggStream
      .keyBy(_.windowEnd)
      .process(new TopNPages(3))

    resultStream.print()
    env.execute("hot pages job")
  }
}

//  KeyedProcessFunction<K, I, O>
class TopNPages(top: Int) extends KeyedProcessFunction[Long, PageViewCount, String] {
  // 先定义一个状态
  lazy val pageViewCount: ListState[PageViewCount] = getRuntimeContext.getListState(new ListStateDescriptor[PageViewCount]("pageViewCount", classOf[PageViewCount]))

  override def processElement(value: PageViewCount, ctx: KeyedProcessFunction[Long, PageViewCount, String]#Context, out: Collector[String]): Unit = {

    // 将数据添加到状态中
    pageViewCount.add(value)
    // 注册一个定时器
    ctx.timerService().registerEventTimeTimer(value.windowEnd + 1)
  }

  // 时间到了做什么事
  override def onTimer(timestamp: Long, ctx: KeyedProcessFunction[Long, PageViewCount, String]#OnTimerContext, out: Collector[String]): Unit = {
    // 将状态中的元素都放到另外一个list中，方便排序
    val listBuffer: ListBuffer[PageViewCount] = ListBuffer()
    val iter = pageViewCount.get().iterator()
    while (iter.hasNext) {
      listBuffer += iter.next()
    }

    pageViewCount.clear()

    // 按照访问量排序并输出top n
    val sortedPageViewCounts = listBuffer.sortWith(_.count > _.count).take(top)

    // 将排名信息格式化成String，便于打印输出可视化展示
    val result: StringBuilder = new StringBuilder
    result.append("窗口结束时间：").append(new Timestamp(timestamp - 1)).append("\n")

    // 遍历结果列表中的每个ItemViewCount，输出到一行
    for (i <- sortedPageViewCounts.indices) {
      val currentItemViewCount = sortedPageViewCounts(i)
      result.append("NO").append(i + 1).append(": \t")
        .append("页面URL = ").append(currentItemViewCount.url).append("\t")
        .append("热门度 = ").append(currentItemViewCount.count).append("\n")
    }

    result.append("\n==================================\n\n")

    Thread.sleep(1000)
    out.collect(result.toString())

    /**
     *
     */

  }
}

// org.apache.flink.streaming.api.scala.function.WindowFunction
// 注意引包不要引错 [IN, OUT, KEY, W <: Window]
// 注意这里的 key 的类型跟热门商品的不一样，区别在于keyBy中的key的方式不一样，可以看看区别
class PageViewCountWindowResult() extends WindowFunction[Long, PageViewCount, String, TimeWindow] {
  // 每来一条数据调用一次
  override def apply(key: String, window: TimeWindow, input: Iterable[Long], out: Collector[PageViewCount]): Unit = {

    val url = key
    val windowEnd = window.getEnd
    val count = input.iterator.next()
    // 封装成样例类
    out.collect(PageViewCount(url, windowEnd, count))
  }
}


// AggregateFunction<IN, ACC, OUT>
// IN: 这里的额in，就是
// ACC:
// OUT:
class PageCountAgg() extends AggregateFunction[ApacheLogEvent, Long, Long] {
  override def createAccumulator(): Long = 0L

  override def add(value: ApacheLogEvent, accumulator: Long): Long = accumulator + 1

  override def getResult(accumulator: Long): Long = accumulator

  override def merge(a: Long, b: Long): Long = a + b
}
