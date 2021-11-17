package com.apitest.a.source

import org.apache.flink.streaming.api.scala._

/**
 * @Description:
 * 从集合或者文件中读取数据
 * @Author: tangrenxin
 * @Date: 2021/10/28 00:51
 */

// 定义样例类，温度传感器
case class SensorReading(id: String, timestamp: Long, temperature: Double)

object Code01SourceTest {

  def main(args: Array[String]): Unit = {
    // 创建运行环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // 1.从集合中读取数据
    val dataList = List(
      SensorReading("sensor_1", 1547718199, 35.8),
      SensorReading("sensor_6", 1547718201, 15.4),
      SensorReading("sensor_7", 1547718202, 6.7),
      SensorReading("sensor_10", 1547718205, 38.1)
    )
    val stream1 = env.fromCollection(dataList)
//    stream1.print()
    // 2.从文件中读取数据
    val stream2 = env.readTextFile("datas/sensor.txt")
    stream2.print()
    env.execute("source test")
  }

}
