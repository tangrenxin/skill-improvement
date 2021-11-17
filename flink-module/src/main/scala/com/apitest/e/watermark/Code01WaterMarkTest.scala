package com.apitest.e.watermark

import com.apitest.c.sink.SensorReading
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

/**
 * @Description:
 * watermark
 * @Author: tangrenxin
 * @Date: 2021/10/30 15:25
 */
object Code01WaterMarkTest {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    // 1.设置时间语义
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    // 设置周期生成watermark的周期时间
    env.getConfig.setAutoWatermarkInterval(500)
    // 读取数据
    val inputStream = env.socketTextStream("localhost", 9999)
    // 转换成样例类（简单转换）
    val dataStream = inputStream.map(data => {
      val arr = data.split(",")
      SensorReading(arr(0), arr(1).toLong, arr(2).toDouble)
    })
      // 2.提取数据中的时间戳作为 计算使用的时间
      // 1）升序数据提取时间戳，参数为时间戳，单位是毫秒，由于测试数据时间戳的单位是秒，这里需要 * 1000
      // .assignAscendingTimestamps(_.timestamp * 1000L)
      // 2）乱序数据提取时间戳，有两种构造方式：
      // AssignerWithPeriodicWatermarks: 周期性的生成 watermark，【一般使用这个】默认周期是200ms,也可以通过setAutoWatermarkInterval设置周期时间
      //    常用的实现类是：BoundedOutOfOrdernessTimestampExtractor(延时时间)
      // AssignerWithPunctuatedWatermarks: 阶段性的生成 watermark，即每来一条数据就生成一个wm
      .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[SensorReading](Time.seconds(3)) {
        override def extractTimestamp(element: SensorReading): Long = {
          element.timestamp * 1000L
        }
      })

    // 放入侧输出流时需要的参数
    val lateTag = new OutputTag[(String, Double, Long)]("late")

    // 每 15 秒统计一次，窗口内各传感器所有温度的最小值，以及最新的时间戳
    val resultDataStream = dataStream
      .map(data => (data.id, data.temperature, data.timestamp))
      .keyBy(_._1) // 按照二元组的第一个元素(id)分组
      .timeWindow(Time.seconds(15))
      // 允许处理迟到1分钟的数据
      .allowedLateness(Time.minutes(1))
      // 延迟超过设定的1分钟时间的数据放入侧输出流 也可以放入 前面定义的 lateTag
      .sideOutputLateData(new OutputTag[(String, Double, Long)]("late"))
      .reduce((r1, r2) => (r1._1, r1._2.min(r2._2), r2._3)) // 开窗后的计算

    // 获取侧输出流里面的数据，后续可以处理这部分数据
    resultDataStream.getSideOutput(new OutputTag[(String, Double, Long)]("late")).print("late")
    resultDataStream.print("data")

    env.execute("window test")
  }

  /**
   * 测试结果：
   * 输入:
   * sensor_1,1547718199,35.8
   * sensor_6,1547718201,15.4
   * sensor_7,1547718202,6.7
   * sensor_10,1547718205,38.1
   * sensor_1,1547718206,32
   * sensor_1,1547718208,36.2
   * sensor_1,1547718210,29.7
   * sensor_1,1547718213,30.9
   * sensor_1,1547718212,29
   * sensor_1,1547718228,24
   * sensor_1,1547718213,19
   * sensor_1,1547718215,27
   * sensor_1,1547718215,26
   * sensor_1,1547718285,28
   * sensor_1,1547718218,24
   * sensor_1,1547718288,50 // 触发测试数据的最后一个窗口关闭
   * sensor_1,1547718216,43 // 延迟一分钟的数据
   *
   * 输出：
   * data> (sensor_1,32.0,1547718208)
   * data> (sensor_7,6.7,1547718202)
   * data> (sensor_6,15.4,1547718201)
   * data> (sensor_10,38.1,1547718205)
   * late> (sensor_1,29.0,154771821)
   * data> (sensor_1,29.7,1547718213)
   * data> (sensor_1,19.0,1547718213)
   * data> (sensor_1,19.0,1547718215)
   * data> (sensor_1,19.0,1547718215)
   * data> (sensor_1,24.0,1547718228)
   * data> (sensor_1,19.0,1547718218)
   * late> (sensor_1,43.0,1547718216) // 输出延迟一分钟的数据
   *
   */
}
