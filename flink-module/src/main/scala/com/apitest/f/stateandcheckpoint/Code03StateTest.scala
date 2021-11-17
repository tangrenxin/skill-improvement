package com.apitest.f.stateandcheckpoint

import java.util

import com.apitest.c.sink.SensorReading
import org.apache.flink.api.common.functions.{RichFlatMapFunction, RichMapFunction}
import org.apache.flink.api.common.state.{ListState, ListStateDescriptor, MapState, MapStateDescriptor, ReducingState, ReducingStateDescriptor, ValueState, ValueStateDescriptor}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.{OutputTag, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.util.Collector

/**
 * @Description:
 * 键控状态的 练习  第二种实现方式
 * 需求：对于温度传感器温度值跳变，超过10度，报警
 * @Author: tangrenxin
 * @Date: 2021/11/16 23:29
 */
object Code03StateTest {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    // 读取数据
    val inputStream = env.socketTextStream("localhost", 9999)
    // 转换成样例类（简单转换）
    val dataStream = inputStream.map(data => {
      val arr = data.split(",")
      SensorReading(arr(0), arr(1).toLong, arr(2).toDouble)
    })
    // 需求：对于温度传感器温度值跳变，超过10度，报警
    val alertStream = dataStream
      .keyBy(_.id)
      //      .flatMap(new TempChangeAlert(10.0))
      // 用flink 自带的方法实现报警
      .flatMapWithState[(String, Double, Double), Double] {
        // 用模式匹配类实现，第一个温度数据和其他温度数据走同的逻辑
        // case (参数1：输入的数据,参数二：状态变量，第一调数据为None) => 返回值：(输出参数：将输出结果保存在list中，状态变量)
        case (data: SensorReading, None) => (List.empty, Some(data.temperature))
        case (data: SensorReading, lastTemp: Some[Double]) => {
          val diff = (data.temperature - lastTemp.get).abs
          if (diff > 10.0) {
            // 报警信息加入 list 返回值
            (List((data.id, lastTemp.get, data.temperature)), Some(data.temperature))
          } else {
            // 不报警
            (List.empty, Some(data.temperature))
          }
        }
      }
    alertStream.print()

    env.execute("State test")
  }
}

//// 实现自定义 的RichFlatMapFunction RichFlatMapFunction[输入类型,输出类型]
//class TempChangeAlert(threshold: Double) extends RichFlatMapFunction[SensorReading, (String, Double, Double)]{
//  // 定义状态 保存上一次的温度值
//  lazy val lasteTempState: ValueState[Double] = getRuntimeContext
//    .getState(new ValueStateDescriptor[Double]("lasttempstate", classOf[Double]))
//
//  override def flatMap(value: SensorReading, out: Collector[(String, Double, Double)]): Unit = {
//    // 获取上一次的温度值
//    val lasteTemp = lasteTempState.value()
//    // 跟最新的温度值求差值作比较
//    val diff = (value.temperature - lasteTemp).abs
//    if (diff > threshold) {
//      out.collect((value.id, lasteTemp, value.temperature))
//    }
//    // 更新状态
//    lasteTempState.update(value.temperature)
//  }
//
//
//}



