package core.c.rdd.f.io

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: load数据
 * @Author: tangrenxin
 * @Date: 2021/3/10 22:41
 */
object Spark02_RDD_IO_Load {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("persist")
    val sc = new SparkContext(sparkConf)

    val rdd1 = sc.textFile("output1")
    println(rdd1.collect().mkString(","))
    val rdd2 = sc.objectFile[(String,Int)]("output2")
    println(rdd2.collect().mkString(","))
    val rdd3 = sc.sequenceFile[String,Int]("output3")
    println(rdd3.collect().mkString(","))
    sc.stop()
  }
}
