package com.b.project.a.hotitemsAnalysis

import org.apache.flink.api.common.functions.AggregateFunction
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.api.common.state.{ListState, ListStateDescriptor}
import org.apache.flink.api.java.tuple.{Tuple, Tuple1}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.WindowedStream
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector
import java.sql.Timestamp
import java.util.Properties

import scala.collection.mutable.ListBuffer

/**
 * @Description:
 * 实时热门商品统计
 * @Author: tangrenxin
 * @Date: 2021/11/20 17:36
 */
object HotItems {

  def main(args: Array[String]): Unit = {

    // 获取环境运行对象
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    // 设置时间语义，以日志里的时间作为我的处理时间
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    // 从文件中读取数据，并转换成样例类，提取时间戳生成WaterMark
    val inputStream = env.readTextFile("/Users/tangrenxin/xiaomiADProject/skill-improvement/flink-module/src/main/resources/UserBehavior.csv")
    // 将数据转换成样例类
    val dataStream: DataStream[UserBehavior] = inputStream
      .map(data => {
        val arr = data.split(",")
        UserBehavior(arr(0).toLong, arr(1).toLong, arr(2).toInt, arr(3), arr(4).toLong)
      })
      // 提取时间戳生成WaterMark：升序数据提取时间戳，参数为时间戳，单位是毫秒，
      .assignAscendingTimestamps(_.timestamp * 1000L)

    // 得到窗口聚合结果：按照商品Id分组，开一个事件窗口，统计商品ID在这个窗口内的count值
    val windowStream = dataStream
      .filter(_.behavior == "pv") // 过滤pv行为
      .keyBy("itemId") // 按照商品ID分组
      .timeWindow(Time.hours(1), Time.minutes(5)) // 设置滑动窗口进行统计

    val aggStream: DataStream[ItemViewCount] = windowStream
      // CountAgg() 函数对数据进行预聚合，来一条数据聚合一次，
      // ItemViewWindowResult() 函数为 当窗口要关闭时调用一次
      .aggregate(new CountAgg(), new ItemViewCountWindowResult())

    // 得到聚合结果后，我们得到了 各个时间窗口中的各个商品ID对应的count数
    // 接下来需要按照窗口分组，收集当前窗口内的商品count数据
    // 状态编程、时间窗口，我们需要用到定时器，定时输出窗口的聚合信息，所以我们直接用process
    val resultStream: DataStream[String] = aggStream
      .keyBy("windowEnd")
      .process(new TopNHotItems(5)) // 自定义处理流程

    resultStream.print()
    env.execute("hot items")
  }

}

// 自定义预聚合函数AggregateFunction，聚合状态就是当前商品的count值
// AggregateFunction<IN, ACC, OUT>
//<IN>  The type of the values that are aggregated (input values) 输入类型
//<ACC> The type of the accumulator (intermediate aggregate state). 聚合状态类型
//<OUT> The type of the aggregated result 聚合后的输出类型
class CountAgg() extends AggregateFunction[UserBehavior, Long, Long] {

  // 创建累加器，这个累加器其实就是我们的 count 状态，初始时，count=0
  override def createAccumulator(): Long = {
    0L
  }

  // 每来一条数据就会调一次 add 方法，在方法中需要定义好 数据来了之后该做什么操作
  // 按照这个需求，我们需要 count+1
  override def add(value: UserBehavior, accumulator: Long): Long = accumulator + 1

  // 获取结果，给后面的WindowFunction做操作，这里的需求，我们返回累加的count数：accumulator
  override def getResult(accumulator: Long): Long = accumulator

  // 主要用来sessionWindow里做窗口合并的时候
  override def merge(a: Long, b: Long): Long = a + b
}

// 自定义窗口函数WindowFunction WindowFunction[IN, OUT, KEY, W <: Window]
// IN :就是 预聚合函数CountAgg() 的输出，即聚合后的count数
// OUT: 输出为 我们定义好输出样例类 ItemViewCount
// KEY: 是分组时keyBy 的字段,这里需要注意，前面keyBy之后得到的类型是 JavaTuple，所以我们需要将类型统一,传的是Tuple 而不是Long
// W <: Window:窗口类型（时间窗口/计数窗口）
class ItemViewCountWindowResult extends WindowFunction[Long, ItemViewCount, Tuple, TimeWindow] {
  override def apply(key: Tuple, window: TimeWindow, input: Iterable[Long], out: Collector[ItemViewCount]): Unit = {
    // itemId 其实就是这里的 key ，但是key是tuple类型，需要转换一下
    val itemId = key.asInstanceOf[Tuple1[Long]].f0

    // window. 可以拿到当前window的各种信息
    val windowEnd = window.getEnd
    // input 就是前面的预聚合函数的返回值，里面其实只有一个值
    val count = input.iterator.next() // 取出预聚合的结果数据 count
    // 包装成我们想要的结果样例类，并输出
    out.collect(ItemViewCount(itemId, windowEnd, count))
  }
}


// 自定义KeyedProcessFunction KeyedProcessFunction<K, I, O>
// K : key的类型，跟前面的函数一样，KeyBy之后的返回值是JavaTuple
// I : 输入的类型,是上一个函数的返回值 ItemViewCount
// O : 输出的类型,这个地方输出为String
class TopNHotItems(topSize: Int) extends KeyedProcessFunction[Tuple, ItemViewCount, String] {

  // 先定义状态：listState
  // 每一个窗口都应该有一个listState保存当前窗口所有的商品对应的count值
  private var itemViewCountListState: ListState[ItemViewCount] = _

  override def open(parameters: Configuration): Unit = {
    itemViewCountListState = getRuntimeContext.getListState(new ListStateDescriptor[ItemViewCount]("itemViewCount-list", classOf[ItemViewCount]))
  }

  // processElement(I value, Context ctx, Collector<O> out)
  // value:每来一条数据都会调一次这个方法，来的数据就是value
  // ctx:上下文信息，可以注册定时器，也可以获取当前的处理时间和watermark
  // out:输出数据
  override def processElement(value: ItemViewCount, ctx: KeyedProcessFunction[Tuple, ItemViewCount, String]#Context, out: Collector[String]): Unit = {
    // 每来一条数据，直接加入 listState
    itemViewCountListState.add(value)
    // 注册一个 windowEnd + 1毫秒 之后触发的定时器 windowEnd 是前面在窗口聚合时取到的 window 关闭的时间戳
    ctx.timerService().registerEventTimeTimer(value.windowEnd + 1)
    // 得到了窗口关闭的时间 windowEnd ，那什么时候去排序，什么时候得输出当前窗口的结果呢？
    // 1毫秒之后。假设当前watermark是九点整，那如果九点过一毫秒这个时刻的watermark来了，那就说明九点之前的所有数据都到齐了
    // 定义计时器
  }
  // 当定时器触发，可以认为所有窗口统计结果都已到齐，可以排序输出了
  // timestamp 是注册定时器是传的 windowEnd + 1
  override def onTimer(timestamp: Long, ctx: KeyedProcessFunction[Tuple, ItemViewCount, String]#OnTimerContext, out: Collector[String]): Unit = {
    // 为了方便排序，另外定义一个 ListBuffer，保存 ListState 里面的所有数据
    val allItemViewCounts: ListBuffer[ItemViewCount] = ListBuffer()
    val iter = itemViewCountListState.get().iterator()
    while(iter.hasNext){
      allItemViewCounts += iter.next()
    }

    // 清空状态
    itemViewCountListState.clear()

    // 按照count大小排序，取前n个
    val sortedItemViewCounts = allItemViewCounts
      .sortBy(_.count)(Ordering.Long.reverse) // 按照 count 排序(只能升序排列)，柯里化函数，将list反转
      .take(topSize)  // 取top数据

    // 将排名信息格式化成String，便于打印输出可视化展示
    val result: StringBuilder = new StringBuilder
    result.append("窗口结束时间：").append( new Timestamp(timestamp - 1) ).append("\n")

    sortedItemViewCounts.size
    // 遍历结果列表中的每个 ItemViewCount，输出到一行
    for( i <- sortedItemViewCounts.indices ){
      val currentItemViewCount = sortedItemViewCounts(i)
      result.append("NO").append(i + 1).append(": \t")
        .append("商品ID = ").append(currentItemViewCount.itemId).append("\t")
        .append("热门度 = ").append(currentItemViewCount.count).append("\n")
    }

    result.append("\n==================================\n\n")

    Thread.sleep(1000)
    out.collect(result.toString())
  }
}

