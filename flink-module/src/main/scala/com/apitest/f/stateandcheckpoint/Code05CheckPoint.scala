package com.apitest.f.stateandcheckpoint

import java.util.concurrent.TimeUnit

import com.apitest.c.sink.SensorReading
import org.apache.flink.api.common.restartstrategy.RestartStrategies
import org.apache.flink.api.common.time.Time
import org.apache.flink.contrib.streaming.state.RocksDBStateBackend
import org.apache.flink.runtime.state.filesystem.FsStateBackend
import org.apache.flink.runtime.state.memory.MemoryStateBackend
import org.apache.flink.streaming.api.CheckpointingMode
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}

/**
 * @Description:
 * CheckPoint 配置
 * 重启策略 配置
 * @Author: tangrenxin
 * @Date: 2021/11/17 23:29
 */
object Code05CheckPoint {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    // =================   checkpoint 配置
    // 开启 checkpoint ，flink默认是不开启的
    env.enableCheckpointing(1000L)
    // 设置checkpoint 级别（EXACTLY_ONCE、AT_LEAST_ONCE）根据场景优化
    env.getCheckpointConfig.setCheckpointingMode(CheckpointingMode.AT_LEAST_ONCE)
    // 设置 checkpoint 超时时间，一般给到分钟级别，每次checkpoint 如果超过了这个时间，就放弃本次checkpoint
    env.getCheckpointConfig.setCheckpointTimeout(60000L)
    // =================   一般配置完以上配置，基本上就OK了,可以看看其他配置：
    // 最多允许同时做几次checkpoint (默认 1 )
    env.getCheckpointConfig.setMaxConcurrentCheckpoints(2)
    // 两次 checkpoint 之间的最小间隔时间  上一次checkpoint的尾与下一次checkpoint头的时间间隔，一旦配置了这个
    // 参数，setMaxConcurrentCheckpoints 的配置就失效了，就只能是 1
    env.getCheckpointConfig.setMinPauseBetweenCheckpoints(500)
    // 是否使用 checkpoint 做故障恢复（除了checkpoint可以做故障恢复以外，还有 savepoint）
    // 默认是false：表示 checkpoint 和 savepoint 那个近用哪个
    // true：表示只用 checkpoint 做故障恢复
    env.getCheckpointConfig.setPreferCheckpointForRecovery(true)
    // checkpoint失败次数容忍度
    env.getCheckpointConfig.setTolerableCheckpointFailureNumber(3)


    // 配置完 checkpoint 后，当程序故障重启时到底要怎么重启呢，需要配置一些重启策略：
    // ==================   重启策略
    // 1.固定时间重启策略（10秒内重启3次）
    env.setRestartStrategy(RestartStrategies.fixedDelayRestart(3,10000))
    // 2.失败率重启策略（重启次数，失败的时间间隔，两次尝试重启的时间间隔）
    env.setRestartStrategy(RestartStrategies.failureRateRestart(5,Time.of(5,TimeUnit.MINUTES),Time.of(10,TimeUnit.SECONDS)))


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




