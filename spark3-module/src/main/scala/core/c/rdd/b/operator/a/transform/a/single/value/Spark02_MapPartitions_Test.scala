package core.c.rdd.b.operator.a.transform.a.single.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: mapPartitions 练习例子：获取分区的最大值
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark02_MapPartitions_Test {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子 mapPartitions
    val rdd = sc.makeRDD(List(1, 2, 3, 4), 2)
    val mpRDD = rdd.mapPartitions(
      // 传递一个迭代器，返回一个迭代器
      iter => {
        // 获取 迭代器中的最大值
        //        iter.max
        // 此时需要返回的是 迭代器，但是iter.max 得到的是一个值，那么如何将其转换成迭代器呢？
        List(iter.max).iterator
      }
    )
    mpRDD.collect().foreach(println)

    sc.stop()

  }
}
