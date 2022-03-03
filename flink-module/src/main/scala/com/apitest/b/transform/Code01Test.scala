package com.apitest.b.transform

import org.apache.flink.api.common.functions.RichFlatMapFunction
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.scala._
import org.apache.flink.util.Collector

/**
 * @Description:
 * keyBy/滚动聚合算子(min,max,sum,minBy,maxBy)/reduce
 * 滚动聚合算子 和 reduce 操作都是在 keyBy之后
 *
 * 这些算子基本都是成对出现的
 * 1. keyBy 和 滚动聚合算子(min,max,sum,minBy,maxBy)
 * 2. keyBy 和 reduce
 * 3. split 和 select
 * 4. connect 和 coMap/flatCoMap
 * @Author: tangrenxin
 * @Date: 2021/10/29 22:39
 */

// 定义样例类，温度传感器
case class SensorReading(id: String, timestamp: Long, temperature: Double)

object Code01Test {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    // 0.读取数据
    val inputStream = env.readTextFile("datas/sensor.txt")
    // 1.先转换成样例类（简单转换）
    val dataStream = inputStream.map(data => {
      val arr = data.split(",")
      SensorReading(arr(0), arr(1).toLong, arr(2).toDouble)
    })
    // 2.分组聚合，输出每个传感器当前最小值
    val aggStream = dataStream
      .keyBy("id") // 根据id进行分组
      //      .min("temperature") // 只有 temperature 是最小值，其他字段的数据 是上一个最小值对应的其他数据
      .minBy("temperature") // 输出最小值对应的整行数据
    //    aggStream.print()

    // 3.需要输出当前最小的温度值，以及最近的时间戳，要用reduce
    val reduceRes = dataStream
      .keyBy("id")
      .reduce((curState, newData) => {
        SensorReading(curState.id, newData.timestamp, curState.temperature.min(newData.temperature))
      })
    // reduceRes.print()

    // 4.多流转换操作
    // 4.1 分流，将传感器数据按照温度高低(以30度为界)，拆分成两个流
    val splitStream = dataStream
      .split(data => {
        // 对温度大于 30 的数据打上 high 的标记，小于30的数据打上 low 标记
        if (data.temperature > 30) Seq("high") else Seq("low")
      })
    //    splitStream.print()
    // split 函数本身只是对输入的数据集进行标记，并没有将数据及真正的实现切分
    // 因此需要借助 select 函数根据标记将数据切分成不同的数据集
    val highTempStream = splitStream.select("high")
    val lowTempStream = splitStream.select("low")
    val allTempStream = splitStream.select("high", "low")
    //    highTempStream.print("high")
    //    lowTempStream.print("low")
    //    allTempStream.print("all")

    // 4.2 合流，connect
    // 两个数据类型不一样的流 也能进行合并，返回的是包含两种数据类型的流 ConnectedStreams[(String, Double), SensorReading]
    val warningStream = highTempStream.map(data => (data.id, data.temperature))
    val connectStream: ConnectedStreams[(String, Double), SensorReading] = warningStream.connect(lowTempStream)

    // 用 coMap 对数据进行分别处理
    val coMapResStream = connectStream
      .map(
        // 返回值的类型也可以不同
        warningData => (warningData._1, warningData._2, "warning"),
        loaTempData => (loaTempData.id, "healthy")
      )
        coMapResStream.print()
    // 4.3 合流，union
    // 只能合并 数据类型一致的流 下面这两个流数据类型不一样，会报错
    //    val unionResStream = warningStream.union(lowTempStream)
    val unionResStream = highTempStream.union(lowTempStream)
    unionResStream.flatMap(new RichFlatMapFunction[SensorReading,SensorReading] {


      override def open(parameters: Configuration): Unit = {
        // 调用flatMap 前调用

        getRuntimeContext
      }

      override def close(): Unit = super.close()

      override def flatMap(value: SensorReading, out: Collector[SensorReading]): Unit = {

      }
    })
//    unionResStream.print()
    env.execute("source test")

  }



}
