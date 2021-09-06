package core.b.wc

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/3/3 00:37
 */
object Spark03_WordCount {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local").setAppName("TRXTest")
    val sc = new SparkContext(sparkConf) //环境对象
    val lines = sc.textFile("datas")
    val words = lines.flatMap(_.split(" "))
    val wordToOne = words.map(word => (word, 1))
    // spark 框架提供了更多的功能，可以将分组和聚合使用一个方法实现
    // reduceByKey 相同的key的数据可以对value进行reduce聚合
    // wordToOne.reduceByKey((x,y)=>{x+y} 与下面等效
    val wordToCount = wordToOne.reduceByKey(_ + _)
    // 5.将转换结果采集到控制台打印出来
    val array = wordToCount.collect() // 收集
    array.foreach(println)
    // 三、关闭连接
    sc.stop()

  }
}
