package core.c.rdd.e.persist

import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: 持久化
 *               演示
 * @Author: tangrenxin
 * @Date: 2021/3/10 20:37
 */
object Spark03_RDD_Persist_Cache {

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
    // 将中间结果 mapRDD 进行缓存
    // cache 底层也是调用的 persist
    // cache 默认持久化的操作，只能将数据保存到内存中，如果想要保存到磁盘文件，需要更改存储级别
    // mapRDD.cache()
    // 设置存储级别
    mapRDD.persist(StorageLevel.DISK_ONLY)
    // 持久化操作，必须在行动算子执行时完成
    val reduceRDD = mapRDD.reduceByKey(_ + _)
    reduceRDD.collect().foreach(println)
    println("********************************")
    val groupRDD = mapRDD.groupByKey()
    groupRDD.collect().foreach(println)
    sc.stop()
  }
}
