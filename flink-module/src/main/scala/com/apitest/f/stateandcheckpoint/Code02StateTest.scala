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
 * 键控状态的 练习
 * 需求：对于温度传感器温度值跳变，超过10度，报警
 * @Author: tangrenxin
 * @Date: 2021/11/16 23:29
 */
object Code02StateTest {

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
      .flatMap(new TempChangeAlert(10.0))

    alertStream.print()

    env.execute("State test")
  }


}

// 实现自定义 的RichFlatMapFunction RichFlatMapFunction[输入类型,输出类型]
class TempChangeAlert(threshold: Double) extends RichFlatMapFunction[SensorReading, (String, Double, Double)]{
  // 定义状态 保存上一次的温度值
  lazy val lasteTempState: ValueState[Double] = getRuntimeContext
    .getState(new ValueStateDescriptor[Double]("lasttempstate", classOf[Double]))

  override def flatMap(value: SensorReading, out: Collector[(String, Double, Double)]): Unit = {
    // 获取上一次的温度值
    val lasteTemp = lasteTempState.value()
    // 跟最新的温度值求差值作比较
    val diff = (value.temperature - lasteTemp).abs
    if (diff > threshold) {
      out.collect((value.id, lasteTemp, value.temperature))
    }
    // 更新状态
    lasteTempState.update(value.temperature)
  }


}



