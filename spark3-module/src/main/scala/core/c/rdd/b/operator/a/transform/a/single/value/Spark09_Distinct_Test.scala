package core.c.rdd.b.operator.a.transform.a.single.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: distinct
 *               不用distinct算子，如何实现数据去重
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark09_Distinct_Test {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子 Sample
    val rdd = sc.makeRDD(List(1, 2, 3, 4, 1, 2, 3, 4))
    // scala 集合的distinct函数去重的原理是，底层是用hashSet实现的，
    // 那么 RDD 中的去重方式什么呢？
    //源码： case _ => map(x => (x, null)).reduceByKey((x, _) => x, numPartitions).map(_._1)

    val valueRDD = rdd.map(x=>(x,null))
    val valueReduceByKeyRDD = valueRDD.reduceByKey((x,y)=>x)
    val resRDD = valueReduceByKeyRDD.map(_._1)
    resRDD.collect().foreach(println)
    sc.stop()

  }
}
