package com.b.project.b.networkflowAnalysis.a.hotPagesNetWorkFlow

import java.sql.Timestamp
import java.text.SimpleDateFormat

import org.apache.flink.api.common.functions.AggregateFunction
import org.apache.flink.api.common.state.{MapState, MapStateDescriptor}
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
 *
 * 优化：乱序数据处理
 * @Author: tangrenxin
 * @Date: 2021/11/20 17:38
 */

object Code03HotPagesNetworkFlowV2 {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    // 设置时间语义，使用日志里的时间
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

    // 读取数据，转换成样例类并提取时间戳和watermark
    // 从 NetWorkFlowTest.csv 中输入数据测试
    val inputStream = env.socketTextStream("localhost", 9999)

    val dataStream = inputStream
      .map(data => {
        val arr = data.split(" ")
        // 对事件时间进行转换，得到时间戳
        val simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss")
        val ts = simpleDateFormat.parse(arr(3)).getTime
        ApacheLogEvent(arr(0), arr(1), ts, arr(5), arr(6))
      })
      // 日志中的数据是乱序数据，提取时间戳和watermark(最大乱序程度)
      // 这个地方设置的waterMark设置太长了，达到一分钟，而需求要没五秒钟输出一次，显然不是很好
      // 使用watermark结合窗口的延迟触发机制处理乱序数据
      // 回想一下：乱序数据的三重保证
      // 1. waterMark
      // 2. 开窗后允许数据延迟一定时间(比如1分钟)：.allowedLateness(Time.minutes(1))
      // 3. 一分钟以外还有延迟数据，将这部分数据输出到侧输出流，后续处理：.sideOutputLateData(new OutputTag[ApacheLogEvent](" late"))
      // .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[ApacheLogEvent](Time.minutes(1)) {
      .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[ApacheLogEvent](Time.seconds(1)) {
        override def extractTimestamp(element: ApacheLogEvent): Long = {
          element.timestamp
        }
      })

    // 进行开窗聚合，以及排序输出
    val aggStream: DataStream[PageViewCount] = dataStream
      .filter(_.method == "GET")
      // 过滤掉静态页面等
      .filter(data => {
        val pattern = "^((?!\\.(css|js)$).)*$".r
        (pattern findFirstIn data.url).nonEmpty
      })
      .keyBy(_.url)
      // 开窗，每5秒输出最近十分钟的热门页面
      .timeWindow(Time.minutes(10), Time.seconds(5))
      // 允许数据延迟一分钟
      .allowedLateness(Time.minutes(1))
      // 一分钟之外还有迟到的数据，将这部分数据输出到侧输出流
      .sideOutputLateData(new OutputTag[ApacheLogEvent]("late"))
      // 聚合函数：先做一个预聚合，后面再做合并window之后包装成样例类的操作
      .aggregate(new PageCountAggV2(), new PageViewCountWindowResultV2())

    // 按照时间窗口聚合，计算出窗口内的热门页面并排序取topN
    val resultStream: DataStream[String] = aggStream
      .keyBy(_.windowEnd)
      .process(new TopNPagesV2(3))

    dataStream.print("data")
    aggStream.print("agg")
    // 获取 侧输出流数据并打印
    aggStream.getSideOutput(new OutputTag[ApacheLogEvent]("late")).print("late")
    resultStream.print()
    env.execute("hot pages job")

    /**
     * 经过测试之后，引入wm+延时处理机制，虽然能对乱序数据做一个很好的处理了
     * 但是最后输出的结果有一个bug：同一url，延迟数据来了之后，会在原来的基础上再次聚合，再输出一条aggStream，详细的看测试表
     *
     * 使用的状态类型是list
     * 多条延时数据来了之后，不断的add，list中自然就出现了重复的url
     * 正常情况下，我们只需要最新的那一条，相当于有更新操作，
     * 这时候，使用list就不合适了，可以使用 map
     * 看下一段代码
     *
     */
  }
}

//  KeyedProcessFunction<K, I, O>
class TopNPagesV2(top: Int) extends KeyedProcessFunction[Long, PageViewCount, String] {
  // 先定义一个状态
  //  lazy val pageViewCountListState: ListState[PageViewCount] = getRuntimeContext.getListState(new ListStateDescriptor[PageViewCount]("pageViewCount", classOf[PageViewCount]))
  lazy val pageViewCountMapState: MapState[String, Long] = getRuntimeContext.getMapState(new MapStateDescriptor[String, Long]("pageViewCount-map", classOf[String], classOf[Long]))

  override def processElement(value: PageViewCount, ctx: KeyedProcessFunction[Long, PageViewCount, String]#Context, out: Collector[String]): Unit = {

    // 将数据添加到状态中
//    pageViewCountListState.add(value)
    pageViewCountMapState.put(value.url,value.count)
    // 注册一个定时器
    ctx.timerService().registerEventTimeTimer(value.windowEnd + 1)
    // 另外注册一个定时器，1分钟之后触发，这时窗口已经彻底关闭，不再有聚合结果输出，可以清空状态
    ctx.timerService().registerEventTimeTimer(value.windowEnd + 60000L)
  }

  // 时间到了做什么事
  override def onTimer(timestamp: Long, ctx: KeyedProcessFunction[Long, PageViewCount, String]#OnTimerContext, out: Collector[String]): Unit = {
    // 将状态中的元素都放到另外一个list中，方便排序
//    val listBuffer: ListBuffer[PageViewCount] = ListBuffer()
//    val iter = pageViewCountListState.get().iterator()
//    while (iter.hasNext) {
//      listBuffer += iter.next()
//    }
//    pageViewCountListState.clear()

    // timestamp 当前定时器的时间戳
    // 判断定时器触发时间，如果已经是窗口结束时间1分钟之后，那么直接清空状态
    if( timestamp == ctx.getCurrentKey + 60000L ){
      pageViewCountMapState.clear()
      return
    }

    val allPageViewCounts: ListBuffer[(String, Long)] = ListBuffer()
    val iter = pageViewCountMapState.entries().iterator()
    while(iter.hasNext){
      val entry = iter.next()
      allPageViewCounts += ((entry.getKey, entry.getValue))
    }




    // 按照访问量排序并输出top n
    val sortedPageViewCounts = allPageViewCounts.sortWith(_._2 > _._2).take(top)

    // 将排名信息格式化成String，便于打印输出可视化展示
    val result: StringBuilder = new StringBuilder
    result.append("窗口结束时间：").append(new Timestamp(timestamp - 1)).append("\n")

    // 遍历结果列表中的每个ItemViewCount，输出到一行
    for (i <- sortedPageViewCounts.indices) {
      val currentItemViewCount = sortedPageViewCounts(i)
      result.append("NO").append(i + 1).append(": \t")
        .append("页面URL = ").append(currentItemViewCount._1).append("\t")
        .append("热门度 = ").append(currentItemViewCount._2).append("\n")
    }

    result.append("\n==================================\n\n")

    Thread.sleep(1000)
    out.collect(result.toString())

  }
}

// org.apache.flink.streaming.api.scala.function.WindowFunction
// 注意引包不要引错 [IN, OUT, KEY, W <: Window]
// 注意这里的 key 的类型跟热门商品的不一样，区别在于keyBy中的key的方式不一样，可以看看区别
class PageViewCountWindowResultV2() extends WindowFunction[Long, PageViewCount, String, TimeWindow] {
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
class PageCountAggV2() extends AggregateFunction[ApacheLogEvent, Long, Long] {
  override def createAccumulator(): Long = 0L

  override def add(value: ApacheLogEvent, accumulator: Long): Long = accumulator + 1

  override def getResult(accumulator: Long): Long = accumulator

  override def merge(a: Long, b: Long): Long = a + b
}
