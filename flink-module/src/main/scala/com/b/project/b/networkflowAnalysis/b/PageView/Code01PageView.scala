package com.b.project.b.networkflowAnalysis.b.PageView

import org.apache.flink.api.common.functions.AggregateFunction
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

/**
 * @Description:
 * PV 统计 的基本实现
 * 1.一个并行度
 * 2.所有的数据都分到同一个组
 * 3.一个小时统计一次，小时级别的PV
 * @Author: tangrenxin
 * @Date: 2021/11/28 下午4:52
 */

// 定义输入数据样例类
case class UserBehavior(userId: Long, itemId: Long, categoryId: Int, behavior: String, timestamp: Long)

// 定义输出pv统计的样例类
case class PvCount(windowEnd: Long, count: Long)

object Code01PageView {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.setParallelism(1)

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
      .map(data => ("pv", 1L))
      .keyBy(_._1) // 所有数据会被分到同一个组
      .timeWindow(Time.hours(1)) // 1小时滚动窗口
      // PvCountAgg() 增量的预聚合统计，PvCountWindowResult窗口结束时调用的函数，包装成我们想要的样例类输出
      .aggregate(new PvCountAgg(), new PvCountWindowResult())

    pvStream.print()

    env.execute("pv job")

    /**
     *  运行结果：
     * PvCount(1511661600000,41890)
     * PvCount(1511665200000,48022)
     * PvCount(1511668800000,47298)
     * PvCount(1511672400000,44499)
     * PvCount(1511676000000,48649)
     * PvCount(1511679600000,50838)
     * PvCount(1511683200000,52296)
     * PvCount(1511686800000,52552)
     * PvCount(1511690400000,48292)
     * PvCount(1511694000000,13)
     */
  }
}

// 自定义预聚合函数
class PvCountAgg() extends AggregateFunction[(String, Long), Long, Long] {
  override def createAccumulator(): Long = 0L

  override def add(value: (String, Long), accumulator: Long): Long = accumulator + 1

  override def getResult(accumulator: Long): Long = accumulator

  override def merge(a: Long, b: Long): Long = a + b
}

// 自定义窗口函数
class PvCountWindowResult() extends WindowFunction[Long, PvCount, String, TimeWindow] {

  // 来一条数据调用一次，包装成输出的样例类
  override def apply(key: String, window: TimeWindow, input: Iterable[Long], out: Collector[PvCount]): Unit = {
    out.collect(PvCount(window.getEnd, input.head))
  }
}


