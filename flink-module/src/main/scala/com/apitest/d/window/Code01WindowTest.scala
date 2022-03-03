package com.apitest.d.window

import com.apitest.c.sink.SensorReading
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/10/30 15:25
 */
object Code01WindowTest {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    // 读取数据
//    val inputStream = env.readTextFile("datas/sensor.txt")
    val inputStream = env.socketTextStream("localhost", 9999)
    // 转换成样例类（简单转换）
    val dataStream = inputStream.map(data => {
      val arr = data.split(",")
      SensorReading(arr(0), arr(1).toLong, arr(2).toDouble)
    })

    // 每 15 秒统计一次，窗口内各传感器所有温度的最小值，以及最新的时间戳
    val resultDataStream = dataStream
      .map(data => (data.id, data.temperature, data.timestamp))
      .keyBy(_._1) // 按照二元组的第一个元素(id)分组
      // 1.滚动时间窗口
      // of有两个构造方法：of(Time size) of(size: Time, offset: Time)
      // size：表示时间窗口的长度   offset：表示时间偏移量（比如遇到时区问题时需要使用）
      // .window(TumblingEventTimeWindows.of(Time.seconds(15)))
      // 2.滑动时间窗口
      // of有两个构造方法：of(size: Time, slide: Time) of(size: Time, slide: Time, offset: Time)
      // size：表示时间窗口的长度  slide：表示窗口滑动长度  offset：表示时间偏移量
      // .window(SlidingEventTimeWindows.of(Time.seconds(15),Time.seconds(10)))
      // 3.会话窗口
      // .window(EventTimeSessionWindows.withGap(Time.seconds(10)))
      // 4.**时间窗口**的简写方法：传一个参数是滚动窗口，传两个参数是滑动窗口（如果需要使用offset，就没法使用简写形式）
      // .timeWindow(Time.seconds(15))
      // 5.**count窗口**的简写方法：传一个参数是滚动窗口，传两个参数是滑动窗口
      // .countWindow(100)
      .timeWindow(Time.seconds(15))
      .reduce((r1, r2) => (r1._1, r1._2.min(r2._2), r2._3)) // 开窗后的计算

    resultDataStream.print()

    env.execute("window test")
  }
}
