package core.e.broadcast

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

/**
 * @Description: 广播变量
 * @Author: tangrenxin
 * @Date: 2021/3/11 01:23
 */
object Spark02_Bc {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    val rdd1 = sc.makeRDD(List(
      ("a", 1), ("b", 2), ("c", 3)
    ))
//    val rdd2 = sc.makeRDD(List(
//      ("a", 4), ("b", 5), ("c", 6)
//    ))

    val map = mutable.Map(("a", 4), ("b", 5), ("c", 6))
    // 封装广播变量
    val bc = sc.broadcast(map)
    rdd1.map {
      case (w, c) => {
        // 访问广播变量
        val l = bc.value.getOrElse(w, 0)
        (w, (c, l))
      }
    }.collect().foreach(println)

    sc.stop()

    /**
     * 闭包数据都是以Task为单位发送的，每个任务中的都包含闭包数据
     * 这样可能导致一个 Executor 中含有大量的重复数据，并且占用大量的内存
     * Executor其实就是一个JVM，所以启动时会自动分配内存
     * 完全可以将能武中的闭包数据放置在Executor的内存中，达到共享的目的
     * Spark中的广播变量可以将闭包的数据保存到Executor的内存中
     * 广播变量不可更改，分布式只读变量
     */

  }
}
