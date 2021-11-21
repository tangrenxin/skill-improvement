package com.b.project.a.hotitemsAnalysis

import java.sql.Timestamp
import java.util.Properties

import org.apache.flink.api.common.functions.AggregateFunction
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.api.common.state.{ListState, ListStateDescriptor}
import org.apache.flink.api.java.tuple.{Tuple, Tuple1}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011
import org.apache.flink.util.Collector

import scala.collection.mutable.ListBuffer

/**
 * @Description:
 * 从kafka读取数据，实时热门商品统计
 * @Author: tangrenxin
 * @Date: 2021/11/20 23:35
 */
object HotItemsFromKafka {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1) //设置并行度
    // 设置时间语义
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    // 从kafka中获取数据
    val properties = new Properties()
    properties.setProperty("bootstrap.servers", "localhost:9092")
    properties.setProperty("group.id", "consumer-group")
    properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

    val inputStream: DataStream[String] = env.addSource(new FlinkKafkaConsumer011[String]("hotitems", new SimpleStringSchema(), properties))

    // 将数据封装成样例类
    val dataStream: DataStream[UserBehavior] = inputStream
      .map(data => {
        val arr = data.split(",")
        UserBehavior(arr(0).toLong, arr(1).toLong, arr(2).toInt, arr(3), arr(4).toLong)
      })
      // 由于数据中的时间戳是升序，设置eventTime，
      .assignAscendingTimestamps(_.timestamp * 1000L)

    // 按照商品id分组，并开窗聚合，获取聚合数据
    val aggStream: DataStream[ItemViewCount] = dataStream
      .filter(_.behavior == "pv")
      .keyBy("itemId")
      .timeWindow(Time.hours(1), Time.minutes(5))
      // 需要实现对数据进行聚合，同时又要定时输出数据(时间窗口的定义：每5分钟输出最近1小时的热门商品)
      // 定义两个自定义函数
      // 预聚合函数: 来一条数据聚合一次，增量聚合函数
      // 时间窗口函数：当时间关闭时调用的时间窗口函数，时间窗口关闭后要做啥->输出当前窗口的热门商品
      // aggregate[ACC: TypeInformation, V: TypeInformation, R: TypeInformation](
      //      preAggregator: AggregateFunction[T, ACC, V], // 预聚合函数
      //      windowFunction: WindowFunction[V, R, K, W] // 时间窗口函数
      .aggregate(new AggItemCountFunction(), new ItemViewCountTimeWindowResult())

    // 得到聚合函数后，需要按照时间窗口分组，取出各个时间窗口中的热门商品（top N）
    // 得到聚合结果后，我们得到了 各个时间窗口中的各个商品ID对应的count数
    // 接下来需要按照窗口分组，收集当前窗口内的商品count数据
    // 状态编程、时间窗口，我们需要用到定时器，定时输出窗口的聚合信息，所以我们直接用process
    val resultStream: DataStream[String] = aggStream
      .keyBy("windowEnd")
      .process(new GetTopNHotItems(10))

    resultStream.print()
    env.execute("hot items")
  }

}

// 自定义KeyedProcessFunction KeyedProcessFunction<K, I, O>
// K : key的类型，跟前面的函数一样，KeyBy之后的返回值是JavaTuple
// I : 输入的类型,是上一个函数的返回值 ItemViewCount
// O : 输出的类型,这个地方输出为String
class GetTopNHotItems(topSize: Int) extends KeyedProcessFunction[Tuple, ItemViewCount, String] {

  // 定义 listState
  var itemViewCountListState: ListState[ItemViewCount] = _

  override def open(parameters: Configuration): Unit = {
    itemViewCountListState = getRuntimeContext.getListState(new ListStateDescriptor[ItemViewCount]("itemViewCountListState", classOf[ItemViewCount]))
  }

  // 来一条是数据需要做的事情
  override def processElement(value: ItemViewCount, ctx: KeyedProcessFunction[Tuple, ItemViewCount, String]#Context, out: Collector[String]): Unit = {
    itemViewCountListState.add(value)
    // 注册一个定时器
    ctx.timerService().registerEventTimeTimer(value.windowEnd + 1)
  }

  // 定义一个定时器，时间窗口关闭时，需要做的事情
  override def onTimer(timestamp: Long, ctx: KeyedProcessFunction[Tuple, ItemViewCount, String]#OnTimerContext, out: Collector[String]): Unit = {

    // 定义另一个 ListBuffer 方便排序
    val allItemViewCounts: ListBuffer[ItemViewCount] = ListBuffer()
    // 将 list 状态中的数据迁移到 listbuffer中，方便后续排序
    val iter = itemViewCountListState.get().iterator()
    while (iter.hasNext) {
      val itemViewCount = iter.next()
      // 将数据添加到list中
      allItemViewCounts += itemViewCount
    }
    // 清空 list 状态中的数据
    itemViewCountListState.clear()

    // 排序
    val resList: ListBuffer[ItemViewCount] = allItemViewCounts.sortBy(_.count)(Ordering.Long.reverse).take(topSize)

    // 开始拼接结果数据
    val result = new StringBuilder()
    result.append("窗口结束时间：").append(new Timestamp(timestamp - 1)).append("\n")

    for(i <- 0 until resList.size){
      val currentItemViewCount = resList(i)
      result.append("NO").append(i + 1).append(": \t")
        .append("商品ID = ").append(currentItemViewCount.itemId).append("\t")
        .append("热门度 = ").append(currentItemViewCount.count).append("\n")
    }
    result.append("\n==================================\n\n")

    Thread.sleep(1000)
    out.collect(result.toString())
  }
}

// 这里定义函数加()和不加能继承的函数不一样，为什么？
// AggregateFunction<IN, ACC, OUT>
// IN: 输入的数据类型类型
// ACC: 数据聚合，涉及到状态，这个维护的聚合状态是什么？对于本需求，就是每种商品的count
// OUT: 输出的数据类型，这里的输出V将是下一个时间窗口函数的输入
class AggItemCountFunction() extends AggregateFunction[UserBehavior, Long, Long] {
  // 聚合函数，创建一个累加器，初始时，这个累加器 的初始值 是 0L
  override def createAccumulator(): Long = 0L

  // 每来一条数据就调用一次add方法，数据来了之后的数据处理
  override def add(value: UserBehavior, accumulator: Long): Long = accumulator + 1

  // 获取聚合函数的结果，也就是 累加器 的value
  override def getResult(accumulator: Long): Long = accumulator

  // 对于 sessionWindow，做窗口合并时使用
  override def merge(a: Long, b: Long): Long = a + b
}

// 自定义窗口函数WindowFunction
// WindowFunction<IN, OUT, KEY, W extends Window>
// IN :就是 预聚合函数CountAgg() 的输出，即聚合后的count数
// OUT: 输出为 我们定义好输出样例类 ItemViewCount
// KEY: 是分组时keyBy 的字段,这里需要注意，前面keyBy之后得到的类型是 JavaTuple，所以我们需要将类型统一,传的是Tuple 而不是Long
// W <: Window:窗口类型（时间窗口/计数窗口）
class ItemViewCountTimeWindowResult() extends WindowFunction[Long, ItemViewCount, Tuple, TimeWindow] {
  // 来一条数据，将数据封装成 ItemViewCount(itemId,windowEnd,count) 输出
  // 其中 itemId 就是keyBy的 key，count是聚合函数的输出，windowEnd是当前窗口的关闭时间，需要从窗口函数中获取
  override def apply(key: Tuple, window: TimeWindow, input: Iterable[Long], out: Collector[ItemViewCount]): Unit = {
    // key 是 keyBy算子后面的key，类型是java的tuple，需要从中将key取出来，由于只有一个key：所以 Tuple1
    val itemId = key.asInstanceOf[Tuple1[Long]].f0
    // 可以从窗口函数中的 window 拿到各种窗口信息
    val windowEnd = window.getEnd
    // 聚合函数的输出，就只有一个value，即count
    val count = input.iterator.next()
    out.collect(ItemViewCount(itemId, windowEnd, count))
  }
}



