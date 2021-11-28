package com.b.project.b.networkflowAnalysis.a.hotPagesNetWorkFlow

import java.text.SimpleDateFormat

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

/**
 * @Description:
 * 实时热门页面流量统计
 *
 * 乱序数据测试，测试过程及分析我记录在 NetWorkFlowTest.csv 中，记得看
 * @Author: tangrenxin
 * @Date: 2021/11/20 17:38
 */

object Code02HotPagesNetworkFlowDataTest {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    // 设置时间语义，使用日志里的时间
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

    // 读取数据，转换成样例类并提取时间戳和watermark
    // 从 NetWorkFlowTest.csv 中输入数据测试
    val inputStream = env.socketTextStream("localhost", 9999)

    val dataStream = inputStream
      .map(data => {
        val arr = data.split(" ")
        // 对事件时间进行转换，得到时间戳
        val simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss")
        val ts = simpleDateFormat.parse(arr(3)).getTime
        ApacheLogEvent(arr(0), arr(1), ts, arr(5), arr(6))
      })
      // 日志中的数据是乱序数据，提取时间戳和watermark(最大乱序程度)
      // 这个地方设置的waterMark设置太长了，达到一分钟，而需求要没五秒钟输出一次，显然不是很好
      // 使用watermark结合窗口的延迟触发机制处理乱序数据
      // 回想一下：乱序数据的三重保证
      // 1. waterMark
      // 2. 开窗后允许数据延迟一定时间(比如1分钟)：.allowedLateness(Time.minutes(1))
      // 3. 一分钟以外还有延迟数据，将这部分数据输出到侧输出流，后续处理：.sideOutputLateData(new OutputTag[ApacheLogEvent](" late"))
      // .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[ApacheLogEvent](Time.minutes(1)) {
      .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[ApacheLogEvent](Time.seconds(1)) {
        override def extractTimestamp(element: ApacheLogEvent): Long = {
          element.timestamp
        }
      })

    // 进行开窗聚合，以及排序输出
    val aggStream: DataStream[PageViewCount] = dataStream
      .filter(_.method == "GET")
      // 过滤掉静态页面等
      .filter(data => {
        val pattern = "^((?!\\.(css|js)$).)*$".r
        (pattern findFirstIn data.url).nonEmpty
      })
      .keyBy(_.url)
      // 开窗，每5秒输出最近十分钟的热门页面
      .timeWindow(Time.minutes(10), Time.seconds(5))
      // 允许数据延迟一分钟
      .allowedLateness(Time.minutes(1))
      // 一分钟之外还有迟到的数据，将这部分数据输出到侧输出流
      .sideOutputLateData(new OutputTag[ApacheLogEvent]("late"))
      // 聚合函数：先做一个预聚合，后面再做合并window之后包装成样例类的操作
      .aggregate(new PageCountAgg(), new PageViewCountWindowResult())

    // 按照时间窗口聚合，计算出窗口内的热门页面并排序取topN
    val resultStream: DataStream[String] = aggStream
      .keyBy(_.windowEnd)
      .process(new TopNPages(3))

    dataStream.print("data")
    aggStream.print("agg")
    // 获取 侧输出流数据并打印
    aggStream.getSideOutput(new OutputTag[ApacheLogEvent]("late")).print("late")
    resultStream.print()
    env.execute("hot pages job")

    /**
     * 经过测试之后，引入wm+延时处理机制，虽然能对乱序数据做一个很好的处理了
     * 但是最后输出的结果有一个bug：同一url，延迟数据来了之后，会在原来的基础上再次聚合，再输出一条aggStream，详细的看测试表
     *
     * 使用的状态类型是list
     * 多条延时数据来了之后，不断的add，list中自然就出现了重复的url
     * 正常情况下，我们只需要最新的那一条，相当于有更新操作，
     * 这时候，使用list就不合适了，可以使用 map
     * 看下一段代码
     *
     */
  }
}
