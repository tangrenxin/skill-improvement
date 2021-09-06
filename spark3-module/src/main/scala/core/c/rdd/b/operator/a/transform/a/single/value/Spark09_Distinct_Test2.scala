package core.c.rdd.b.operator.a.transform.a.single.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: distinct
 *               不用distinct算子，如何实现数据去重
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark09_Distinct_Test2 {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子 Sample
    val rdd = sc.makeRDD(List(List(1, 2), List(3, 4), 1,List(1, 2), List(3, 4),List(3, 4, 5)))
    val resRDD = rdd.distinct()
    resRDD.collect().foreach(println)
    sc.stop()

  }
}
