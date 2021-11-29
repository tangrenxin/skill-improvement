package com.b.project.b.networkflowAnalysis.b.PageView

import org.apache.flink.api.common.functions.{AggregateFunction, MapFunction}
import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

import scala.util.Random

/**
 * @Description:
 * PV 统计 的优化版本
 * 1.4个并行度
 * 2.随机生成key，keyby后数据会被分到不同的节点执行
 * 3.然后在根据windowEnd分组，求和，将数据保存在一个状态中，
 * 4.定义一个定时器，当触发定时器时，输出状态中统计的结果，清空状态
 *
 * 这样就实现了并行统计PV的操作
 * @Author: tangrenxin
 * @Date: 2021/11/28 下午4:52
 */

object Code02PageView2 {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
//    env.setParallelism(1)

    // 从文件中读取数据
    val resource = getClass.getResource("/UserBehavior.csv")
    val inputStream: DataStream[String] = env.readTextFile(resource.getPath)

    // 转换成样例类类型并提取时间戳和watermark
    val dataStream: DataStream[UserBehavior] = inputStream
      .map(data => {
        val arr = data.split(",")
        UserBehavior(arr(0).toLong, arr(1).toLong, arr(2).toInt, arr(3), arr(4).toLong)
      })
      .assignAscendingTimestamps(_.timestamp * 1000L)

    val pvStream = dataStream
      .filter(_.behavior == "pv")
      //      .map(data => ("pv", 1L)) // 所有数据会被分到同一个组，并不能发挥并行的优势
      .map(new MyMapper()) // 改进1，打散key
      .keyBy(_._1)
      .timeWindow(Time.hours(1)) // 1小时滚动窗口
      // PvCountAgg() 增量的预聚合统计，PvCountWindowResult窗口结束时调用的函数，包装成我们想要的样例类输出
      .aggregate(new PvCountAgg(), new PvCountWindowResult())

    val totalPvStream = pvStream
      .keyBy(_.windowEnd)
      .process(new TotalPvCountResult())
    totalPvStream.print()

    env.execute("pv job2")

    /**
     * 运行结果：
     * 3> PvCount(1511665200000,48022)
     * 3> PvCount(1511694000000,13)
     * 1> PvCount(1511676000000,48649)
     * 4> PvCount(1511668800000,47298)
     * 4> PvCount(1511672400000,44499)
     * 4> PvCount(1511683200000,52296)
     * 2> PvCount(1511661600000,41890)
     * 2> PvCount(1511679600000,50838)
     * 2> PvCount(1511686800000,52552)
     * 2> PvCount(1511690400000,48292)
     */
  }
}

class MyMapper() extends MapFunction[UserBehavior, (String, Long)] {
  override def map(value: UserBehavior): (String, Long) = {
    // 随机生成元组数据的key
    (Random.nextString(10), 1L)
  }
}

// <K, I, O>
class TotalPvCountResult() extends KeyedProcessFunction[Long, PvCount, PvCount] {
  // 定义一个状态，保存当前所有count总和
  lazy val totalPvCountResultState: ValueState[Long] = getRuntimeContext.getState(new ValueStateDescriptor[Long]("total-pv", classOf[Long]))

  override def processElement(value: PvCount, ctx: KeyedProcessFunction[Long, PvCount, PvCount]#Context, out: Collector[PvCount]): Unit = {
    // 每来一个数据，将count值叠加在当前的状态上
    val currentTotalCount = totalPvCountResultState.value()
    totalPvCountResultState.update(currentTotalCount + value.count)

    // 注册一个 windowEnd+1ms后触发的定时器
    ctx.timerService().registerEventTimeTimer(value.windowEnd + 1)
  }

  // 实现定时器触发时调用的方法
  override def onTimer(timestamp: Long, ctx: KeyedProcessFunction[Long, PvCount, PvCount]#OnTimerContext, out: Collector[PvCount]): Unit = {
    // 从状态中取出pv数据
    val totalPvCount = totalPvCountResultState.value()
    // 包装成样例类
    out.collect(PvCount(ctx.getCurrentKey, totalPvCount))
    // 清理状态
    totalPvCountResultState.clear()
  }
}


