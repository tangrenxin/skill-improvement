package core.c.rdd.e.persist

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: 持久化
 *               演示
 * @Author: tangrenxin
 * @Date: 2021/3/10 20:37
 */
object Spark01_RDD_Persist {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("persist")
    val sc = new SparkContext(sparkConf)

    val list = List("hello spark", "hello scala")
    val rdd = sc.makeRDD(list)
    val flatRDD = rdd.flatMap(_.split(" "))
    val mapRDD = flatRDD.map((_, 1))
    val reduceRDD = mapRDD.reduceByKey(_ + _)
    reduceRDD.collect().foreach(println)
    println("********************************")
    val list1 = List("hello spark", "hello scala")
    val rdd1 = sc.makeRDD(list1)
    val flatRDD1 = rdd1.flatMap(_.split(" "))
    val mapRDD1 = flatRDD1.map((_, 1))
    val groupRDD = mapRDD1.groupByKey()
    groupRDD.collect().foreach(println)

    // 可以看到groupRDD 和 reduceRDD 前面的步骤完全是一样的，只有最后一步不一样，那么可以简化一下，看下一demo
    sc.stop()
  }
}
