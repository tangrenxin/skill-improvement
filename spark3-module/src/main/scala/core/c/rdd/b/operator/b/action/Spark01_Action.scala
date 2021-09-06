package core.c.rdd.b.operator.b.action

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: 行动算子
 *              reduce
 *              collect
 *              count
 *              first
 *              take
 *              takeOrdered
 * @Author: tangrenxin
 * @Date: 2021/3/10 02:33
 */
object Spark01_Action {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(List(1, 2, 3, 4))
    // reduce
    //    println(rdd.reduce(_ + _))

    // collect:采集，会将不同分区的数据按照分区顺序采集到Driver端内存中，形成数组
    //    println(rdd.collect())

    // count:数据源中数据的个数
    //    println(rdd.count())

    // first:获取数据源中的第一个
    //    println(rdd.first())

    // take 获取N个数据
    //    println(rdd.take(3))

    // takeOrdered 获取排序后的N个数据
    val rdd1 = sc.makeRDD(List(2, 4, 3, 1))
    rdd1.takeOrdered(3).foreach(println)
    sc.stop()
  }
}
