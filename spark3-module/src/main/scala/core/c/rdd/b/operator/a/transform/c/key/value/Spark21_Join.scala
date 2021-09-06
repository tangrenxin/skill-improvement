package core.c.rdd.b.operator.a.transform.c.key.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: key-value类型
 *               join
 *               在类型为(K,V)和(K,W)的 RDD 上调用，返回一个相同 key 对应的所有元素连接在一起的
 *               (K,(V,W))的 RDD
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark21_Join {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子
    val rdd = sc.makeRDD(List(
      ("a", 1), ("c", 2), ("b", 3)))
    val rdd2 = sc.makeRDD(List(
      ("b", 4), ("a", 5), ("a", 6)))

    // join ：两个不同数据源的数据，相同的key的value会连接在一起，形成元组
    // 如果两个数据源中key没有匹配上，那么数据不会出现在结果中
    // 如果两个数据源中key有多个相同的，会依次匹配，可能会出现笛卡尔积，数据量会几何性增长，会导致性能降低。
    val joinRDD = rdd.join(rdd2)
    joinRDD.collect().foreach(println)
    sc.stop()

  }
}
