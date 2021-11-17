package com.apitest.c.sink

import org.apache.flink.api.common.serialization.SimpleStringEncoder
import org.apache.flink.core.fs.Path
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink
import org.apache.flink.streaming.api.scala._

/**
 * @Description:
 * 往文件中写入数据，两种方式
 * @Author: tangrenxin
 * @Date: 2021/10/30 00:06
 */

// 定义样例类，温度传感器
case class SensorReading(id: String, timestamp: Long, temperature: Double)

object Code01FileSink {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    // 读取数据
    val inputStream = env.readTextFile("datas/sensor.txt")
    // 转换成样例类（简单转换）
    val dataStream = inputStream.map(data => {
      val arr = data.split(",")
      SensorReading(arr(0), arr(1).toLong, arr(2).toDouble)
    })

    // 方法一：已弃用
    // dataStream.writeAsCsv("datas/sensor.out")
    dataStream.addSink(StreamingFileSink.forRowFormat(
      new Path("datas/sensor1.out"),
      new SimpleStringEncoder[SensorReading]()
    ).build())

    env.execute("sink test")
  }
}
