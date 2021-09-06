package core.b.wc

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/3/3 00:37
 */
object Spark02_WordCount {

  def main(args: Array[String]): Unit = {
    // Application
    // Spark 框架 是一个环境，用来运行spark程序的环境
    // 一、建立和spark框架的连接
    // SparkConf 是spark中的一些基础配置对象  .setMaster() 设置spark框架的运行环境
    val sparkConf = new SparkConf().setMaster("local").setAppName("TRXTest")
    val sc = new SparkContext(sparkConf) //环境对象
    // 二、执行业务操作
    val lines = sc.textFile("datas")
    val words = lines.flatMap(_.split(" "))
    val wordToOne = words.map(word => (word, 1))
    val wordGroup = wordToOne.groupBy(t => t._1)

    // 4.对分组后的数据进行转换（聚合，求和...）在scala中将A变成B就是转换的意思
    // （hello,3）,(world,2)
    val wordToCount = wordGroup.map { // 模式匹配
      case (word, list) => {
        val wordToCount = list.reduce(
          (t1, t2) => {
            (t1._1, t1._2 + t2._2)
          }
        )
      }
    }
    // 5.将转换结果采集到控制台打印出来
    val array = wordToCount.collect() // 收集
    array.foreach(println)
    // 三、关闭连接
    sc.stop()

  }
}
