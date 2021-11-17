package com.apitest.a.source

import java.util.{Properties, Random}

import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.scala._

/**
 * @Description:
 * 从自定义source中读取数据
 * @Author: tangrenxin
 * @Date: 2021/10/28 00:51
 */

object Code03SourceTestFromMysource {

  def main(args: Array[String]): Unit = {
    // 创建运行环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    // 4. 自定义Source
    val stream4 = env.addSource(new MySensorSource())
    stream4.print()
    env.execute("source test")
  }

}

// 自定义SourceFunction
class MySensorSource() extends SourceFunction[SensorReading] {
  // 定义一个标识位flag，用来表示数据源是否正常运行发出数据
  var running: Boolean = true

  override def cancel(): Unit = running = false

  override def run(ctx: SourceFunction.SourceContext[SensorReading]): Unit = {
    // 定义一个随机数发生器
    val rand = new Random()

    // 随机生成一组（10个）传感器的初始温度: （id，temp）
    var curTemp = 1.to(10).map(i => ("sensor_" + i, rand.nextDouble() * 100))

    // 定义无限循环，不停地产生数据，除非被cancel
    while (running) {
      // 在上次数据基础上微调，更新温度值
      curTemp = curTemp.map(
        data => (data._1, data._2 + rand.nextGaussian())
      )
      // 获取当前时间戳，加入到数据中，调用ctx.collect发出数据
      val curTime = System.currentTimeMillis()
      curTemp.foreach(
        data => ctx.collect(SensorReading(data._1, curTime, data._2))
      )
      // 间隔1000ms
      Thread.sleep(1000)
    }
  }
}
