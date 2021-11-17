package com.a.wc

import org.apache.flink.api.scala._

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/10/27 00:01
 */
object WordCount {

  def main(args: Array[String]): Unit = {
    // 创建运行环境
    val env = ExecutionEnvironment.getExecutionEnvironment

    // 读取数据文件
    val inputData = env.readTextFile("datas/word.txt")
    val res:DataSet[(String,Int)] = inputData
      // 需要引入隐式转换，不然会报错 import org.apache.flink.api.scala._
      .flatMap(_.split(" "))
      .map((_, 1))
      .groupBy(0)
      .sum(1)
    res.print()

  }

}
