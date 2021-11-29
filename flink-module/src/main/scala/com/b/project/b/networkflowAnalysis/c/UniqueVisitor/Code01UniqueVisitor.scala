package com.b.project.b.networkflowAnalysis.c.UniqueVisitor

import com.b.project.b.networkflowAnalysis.b.PageView.UserBehavior
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.AllWindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector

/**
 * @Description:
 * UV 统计的基本实现
 * 1.使用窗口函数实现统计(之前没用过，练习使用一下)
 * @Author: tangrenxin
 * @Date: 2021/11/28 下午5:47
 */

// 定义输出Uv统计样例类
case class UvCount(windowEnd: Long, count: Long)

object Code01UniqueVisitor {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.setParallelism(1) // 并行设为1 方便查看结果

    // 从文件中读取数据
    val resource = getClass.getResource("/UserBehavior.csv")
    val inputStream: DataStream[String] = env.readTextFile(resource.getPath)

    // 转换成样例类类型并提取时间戳和watermark
    val dataStream: DataStream[UserBehavior] = inputStream
      .map(data => {
        val arr = data.split(",")
        UserBehavior(arr(0).toLong, arr(1).toLong, arr(2).toInt, arr(3), arr(4).toLong)
      })
      .assignAscendingTimestamps(_.timestamp * 1000L)

    val uvStream = dataStream
      .filter(_.behavior == "pv")
      .timeWindowAll(Time.hours(1)) // 直接不分组，基于DataStream开1小时滚动窗口
      .apply(new UvCountResult())

    uvStream.print()

    env.execute("UV job")

    /**
     * 运行结果：
     * UvCount(1511661600000,28196)
     * UvCount(1511665200000,32160)
     * UvCount(1511668800000,32233)
     * UvCount(1511672400000,30615)
     * UvCount(1511676000000,32747)
     * UvCount(1511679600000,33898)
     * UvCount(1511683200000,34631)
     * UvCount(1511686800000,34746)
     * UvCount(1511690400000,32356)
     * UvCount(1511694000000,13)
     * 可以跟PV对比一下，是经过去重的
     *
     * 思考：
     * 1.AllWindowFunction 这个函数是先将这个小时的所有数据都存成状态，待窗口关闭时再做计算，如果窗口内的数据量特别大怎么办？
     * 2.在AllWindowFunction的apply中定义了一个set数据结构用来去重，如果数据量特别大怎么办？
     *
     * 我们来估算一下数据量，假设一个窗口有 1亿(10^8) 个userID，来看看需要多大的存储空间:
     * 本例中，userId是一个long，生产环境下，一般都是一个很长的字符串类型，两中情况分别估算一下数据量(10^6 大概是1MB)
     * 1)如果id是long，一个数需要2^3B，那么1亿就是 10^8 * 2^3B = 100 * 1MB *8 = 800M （最小情况下）
     * 2)如果id是String，一个数需要100B，那么1亿就是 10^8 * 100B = 100 * 1MB *100 = 10G （一般情况下）
     *
     * 可以看到，最小情况下，一个窗口也需要 800M的空间，最大情况下10G那更不能接受，
     * 所以，在这种数据量情况下，使用全窗口函数和set是不可行的，有哪些解决办法呢？
     *
     * 想到的解决方案：
     * 1.全窗口函数换成增量窗口函数，来一条数据计算一条，存一个状态。可行，但是数据量还是很大。
     * 2.set不存为状态，改用redis，也可以，但是得想想，一个窗口就要占用10G，一天中有24个窗口，算下来也240G了，
     * redis的使用成本是非常高的，明显也不是很好。
     *
     * 所以，遇到这种超大数据量的时，如果要做数据去重的话，将去重逻辑直接放在内存或者放在redis都不是一个好的选择
     * 怎么办呢？有一种特殊的数据结构：BitMap
     * 对应的工具叫做布隆过滤器
     */
  }
}
// 自定义实现全窗口函数，用一个Set结构来保存所有的userId，进行自动去重
class UvCountResult() extends AllWindowFunction[UserBehavior, UvCount, TimeWindow]{
  override def apply(window: TimeWindow, input: Iterable[UserBehavior], out: Collector[UvCount]): Unit = {
    // 定义一个Set
    var userIdSet = Set[Long]()

    // 遍历窗口中的所有数据，把userId添加到set中，自动去重
    for( userBehavior <- input )
      userIdSet += userBehavior.userId

    // 将set的size作为去重后的uv值输出
    out.collect(UvCount(window.getEnd, userIdSet.size))
  }
}
