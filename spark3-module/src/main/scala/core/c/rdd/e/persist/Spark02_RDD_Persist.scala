package core.c.rdd.e.persist

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: 持久化
 *               演示
 * @Author: tangrenxin
 * @Date: 2021/3/10 20:37
 */
object Spark02_RDD_Persist {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("persist")
    val sc = new SparkContext(sparkConf)

    val list = List("hello spark", "hello scala")
    val rdd = sc.makeRDD(list)
    val flatRDD = rdd.flatMap(_.split(" "))
    val mapRDD = flatRDD.map(
      word => {
        // 为了证明 RDD对象被重用时，会重新读取数据，打印标识
        println("@@@@@@@@@@@")
        (word, 1)
      })
    val reduceRDD = mapRDD.reduceByKey(_ + _)
    reduceRDD.collect().foreach(println)
    println("********************************")
    val groupRDD = mapRDD.groupByKey()
    groupRDD.collect().foreach(println)
    // 把步骤相同的部分代码去掉，得到的结果依然相同，即变量对象可以"重用"起来
    // 表面上看着是重用了，但是底层并不是重用，
    // 我们知道，RDD是不存储数据的
    // 所以在执行完mapRDD.reduceByKey(_ + _) 再执行mapRDD.groupByKey()的时候
    // 程序会再将mapRDD前面的流程再走一遍
    // 所以，如果一个RDD需要重复使用，那么需要从头再次执行来获取数据
    // RDD 对象可以重用，但是数据是无法重用的
    // 这样如果数据量比较大，并且前面的流程比较复杂的情况下，效率会非常低下，如何解决这个问题呢？
    // 解：持久化操作（将中间结果保存到内存/文件）next demo
    sc.stop()
  }
}
