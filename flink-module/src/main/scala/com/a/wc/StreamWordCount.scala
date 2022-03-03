package com.a.wc

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala._

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/10/27 00:47
 */
object StreamWordCount {

  def main(args: Array[String]): Unit = {
    // 创建流处理的执行环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment


    // val data = env.socketTextStream("localhost", 9999)
    // 从外部命令中提取参数，作为socket主机名和端口号
    val paraTool = ParameterTool.fromArgs(args)
    val host = paraTool.get("host")
    val part = paraTool.getInt("part")
    val data: DataStream[String] = env.socketTextStream(host, part)
    val res = data.flatMap(_.split(" "))
      .map((_, 1))
      .keyBy(0)
      .sum(1)
    res.print()
    env.execute("Stream Word Count")

  }

}
