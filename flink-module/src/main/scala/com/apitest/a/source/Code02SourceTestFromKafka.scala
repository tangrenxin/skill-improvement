package com.apitest.a.source

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011

/**
 * @Description:
 * 从kafka中读取数据
 * @Author: tangrenxin
 * @Date: 2021/10/28 00:51
 */

object Code02SourceTestFromKafka {

  def main(args: Array[String]): Unit = {
    // 创建运行环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // 从kafka中读取数据 需要传递 topic 反序列化模式 配置参数
    val properties = new Properties()
    properties.setProperty("bootstrap.servers", "localhost:9092")
    properties.setProperty("group.id", "consumer-group")
    val stream = env.addSource(new FlinkKafkaConsumer011[String]("sensor",new SimpleStringSchema(),properties))
    stream.print()
    env.execute("source test")
  }

}
