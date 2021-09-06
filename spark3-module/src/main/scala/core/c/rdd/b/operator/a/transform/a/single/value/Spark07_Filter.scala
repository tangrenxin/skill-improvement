package core.c.rdd.b.operator.a.transform.a.single.value

import org.apache.spark.{SparkConf, SparkContext}
/**
 * @Description: filter
 *               将数据根据指定的规则进行筛选过滤，符合规则的数据保留，不符合规则的数据丢弃。
 *               注意：当数据进行筛选过滤后，分区不变，但是分区内的数据可能不均衡，生产环境下，可能会出现数据倾斜。
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark07_Filter {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子 groupBy
    val rdd = sc.makeRDD(List(1, 2, 3, 4))
    val filterRDD = rdd.filter(_ % 2 == 1)
    filterRDD.collect().foreach(println)
    sc.stop()

  }
}
