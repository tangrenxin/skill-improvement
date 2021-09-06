package core.e.broadcast

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

/**
 * @Description: 引入广播变量前演示
 * @Author: tangrenxin
 * @Date: 2021/3/11 01:23
 */
object Spark01_Bc {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    val rdd1 = sc.makeRDD(List(
      ("a", 1), ("b", 2), ("c", 3)
    ))
    val rdd2 = sc.makeRDD(List(
      ("a", 4), ("b", 5), ("c", 6)
    ))

    /**
     * join会导致数据量几何增长，并且会影响Shuffle的性能  不推荐使用
     * ("a", 1), ("b", 2), ("c", 3)
     * join
     *  ("a", 4), ("b", 5), ("c", 6)
     *  得到
     * (a,(1,4))
     * (b,(2,5))
     * (c,(3,6))
     * 看上去就是结构转换，能不能用别的方式来实现呢？Map
     */
//    val joinRDD = rdd1.join(rdd2)
//    joinRDD.collect().foreach(println)


    /**
     * 这种方式也能实现上述的功能，并且避免了join，进而避免了Shuffle，性能很好
     * 但是，如果rdd1、mutable.Map的数据量很多怎么办
     * 假设driver有十个分区的数据，那么意味着将会有十个任务，这十个任务都会执行下面的map操作，
     * 也就是说，每个任务都会包含闭包数据
     * 这样这样可能会导致，一个 Executor 中含有大量重复的数据，并且占用大量的内存
     */
    val map = mutable.Map(("a", 4), ("b", 5), ("c", 6))
    rdd1.map {
      case (w, c) => {
        val l = map.getOrElse(w, 0)// 闭包数据：函数内部使用到了外部的变量
        (w, (c, l))
      }
    }.collect().foreach(println)

    /**
     * 闭包数据，都是以Task为单位发送的，每个任务中包含闭包数据
     * 这样可能会导致，一个 Executor 中含有大量重复的数据，并且占用大量的内存
     * Executor其实就是一个JVM，所以在启动时，会自动分配内存
     * 完全可以将任务中的闭包数据放置在Executor的内存中，达到共享的目的
     * spark中的广播变量就是可以将闭包数据保存在Executor 的内存中
     * spark中的广播变量不能够更改：分布式共享只读变量
     */

    sc.stop()

  }
}
