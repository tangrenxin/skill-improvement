package com.apitest.c.sink

import java.util.Properties

import org.apache.flink.streaming.api.scala._
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.connectors.kafka.{FlinkKafkaConsumer011, FlinkKafkaProducer011}

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/10/30 00:06
 */

object Code02KafkaSink {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    // 读取数据
    //    val inputStream = env.readTextFile("datas/sensor.txt")
    // 从kafka中读取数据 需要传递 topic 反序列化模式 配置参数
    val properties = new Properties()
    properties.setProperty("bootstrap.servers", "localhost:9092")
    properties.setProperty("group.id", "consumer-group")
    val inputStream = env.addSource(new FlinkKafkaConsumer011[String]("sensor", new SimpleStringSchema(), properties))

    // 转换成样例类（简单转换）
    val dataStream = inputStream.map(data => {
      val arr = data.split(",")
      SensorReading(arr(0), arr(1).toLong, arr(2).toDouble).toString
    })

    dataStream.print()

    dataStream.addSink(new FlinkKafkaProducer011[String](
      "localhost:9092",
      "flinkKafkaSink",
      new SimpleStringSchema()))

    env.execute("kafka sink test")
  }
}
