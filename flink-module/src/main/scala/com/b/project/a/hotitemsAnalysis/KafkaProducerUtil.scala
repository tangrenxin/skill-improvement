package com.b.project.a.hotitemsAnalysis

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

/**
 * @Description:
 * 读取文件中的数据写入kafka
 * @Author: tangrenxin
 * @Date: 2021/11/20 23:33
 */
object KafkaProducerUtil {


  def main(args: Array[String]): Unit = {
    writeToKafka("hotitems")
  }
  def writeToKafka(topic: String): Unit ={
    val properties = new Properties()
    properties.setProperty("bootstrap.servers", "localhost:9092")
    properties.setProperty("key.serializer",
      "org.apache.kafka.common.serialization.StringSerializer")
    properties.setProperty("value.serializer",
      "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](properties)

    // 从文件读取数据，逐行写入kafka
    val bufferedSource = io.Source.fromFile("/Users/tangrenxin/xiaomiADProject/skill-improvement/flink-module/src/main/resources/UserBehavior.csv")

    for( line <- bufferedSource.getLines() ){
      val record = new ProducerRecord[String, String](topic, line)
      producer.send(record)
    }

    producer.close()
  }
}
