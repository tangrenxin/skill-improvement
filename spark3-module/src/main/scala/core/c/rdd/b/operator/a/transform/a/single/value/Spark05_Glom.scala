package core.c.rdd.b.operator.a.transform.a.single.value

import org.apache.spark.{SparkConf, SparkContext}
/**
 * @Description: glom
 *              将同一个分区的数据直接转换为相同类型的内存数组进行处理，分区不变
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark05_Glom {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子 glom 不设定分区时，默认使用最大分区
    val rdd = sc.makeRDD(List(1,2,3,4),2)

    // 将各个分区的数据转化成相同数据类型的数组
    val glomRDD = rdd.glom()
//    glomRDD.collect().foreach(println)
    glomRDD.collect().foreach(data=> println(data.mkString(",")))

    sc.stop()

  }
}
