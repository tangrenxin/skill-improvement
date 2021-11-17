package com.apitest.f.stateandcheckpoint

import java.util

import com.apitest.c.sink.SensorReading
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.common.state.{ListState, ListStateDescriptor, MapState, MapStateDescriptor, ReducingState, ReducingStateDescriptor, ValueState, ValueStateDescriptor}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.{OutputTag, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.windowing.time.Time

/**
 * @Description:
 *              键控状态的定义示例
 * @Author: tangrenxin
 * @Date: 2021/11/16 23:29
 */
object Code01StateTest {

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

    env.execute("State test")
  }

}

// Keyed state 测试：必须定义在 RichFunction 中，因为需要运行时上下文
class MyRichMapper extends RichMapFunction[SensorReading, String] {

  // 定义一个 单值类型 的状态
  var valueState: ValueState[Double] = _
  // 还有一种定义状态的方式可以不用重写open方法：lazy
  //  lazy val valueState2: ValueState[Double] = getRuntimeContext.getState(new ValueStateDescriptor[Double]("valuestate", classOf[Double]))

  // 定义一个 ListState
  lazy val listState: ListState[Int] = getRuntimeContext.getListState(new ListStateDescriptor[Int]("liststate", classOf[Int]))

  // 定义一个 MapState
  lazy val mapState: MapState[String, Double] = getRuntimeContext.getMapState(new MapStateDescriptor[String, Double]("mapstate", classOf[String], classOf[Double]))

  // 定义一个 reduce state reduce 需要类型一致
  //  lazy val reduceState: ReducingState[SensorReading] = getRuntimeContext.getReducingState(new ReducingStateDescriptor[SensorReading]("reducestate",聚合方法,classOf[SensorReading]))

  override def open(parameters: Configuration): Unit = {
    valueState = getRuntimeContext.getState(new ValueStateDescriptor[Double]("valuestate", classOf[Double]))

  }

  override def map(value: SensorReading): String = {
    // value State 的读写
    val myV = valueState.value()
    valueState.update(value.temperature)

    // list state 的读写
    val listV = listState.get()
    listState.add(1)
    val list = new util.ArrayList[Int]()
    list.add(1)
    list.add(2)
    listState.addAll(list)

    // mapState 的读写
    val d = mapState.get("key")
    mapState.put("key", 8)
    mapState.contains("key")
    mapState.isEmpty

    value.id
  }

}



