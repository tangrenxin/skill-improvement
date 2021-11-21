package com.apitest.f.stateandcheckpoint

import com.apitest.c.sink.SensorReading
import org.apache.flink.contrib.streaming.state.RocksDBStateBackend
import org.apache.flink.runtime.state.filesystem.FsStateBackend
import org.apache.flink.runtime.state.memory.MemoryStateBackend
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}

/**
 * @Description:
 * 状态后端的配置
 * 进行状态后端编程时，创建的那些状态存放在哪里？
 * 状态后端有哪些？
 * 1.内存内存级的状态后端，会将键控状态作为内存中的对象进行管理，
 *    将它们存储 在 TaskManager 的 JVM 堆上；而将 checkpoint 存储在 JobManager 的内存中。
 * 2.FsStateBackend 将 checkpoint 存到远程的持久化文件系统（FileSystem）上。
 *    而对于本地状态， 跟 MemoryStateBackend 一样，也会存在 TaskManager 的 JVM 堆上。
 * 3.RocksDBStateBackend
 *    将所有状态序列化后，存入本地的 RocksDB 中存储。 注意：RocksDB 的支持并不直接包含在 flink 中，需要引入依赖
 *
 * @Author: tangrenxin
 * @Date: 2021/11/16 23:29
 */
object Code04StateBackend {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    //  ** 在这里配置状态后端 **
    // - 内存
//    env.setStateBackend(new MemoryStateBackend())
//    // - FS
//    env.setStateBackend(new FsStateBackend("HDFSURL")())
//    // - RocksDB
//    env.setStateBackend(new RocksDBStateBackend(""))


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




