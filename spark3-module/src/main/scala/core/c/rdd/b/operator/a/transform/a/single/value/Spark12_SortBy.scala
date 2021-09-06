package core.c.rdd.b.operator.a.transform.a.single.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: sortBy
 *               排序
 *               该操作用于排序数据。在排序之前，可以将数据通过 f 函数进行处理，之后按照 f 函数处理
 *               的结果进行排序，默认为升序排列。排序后新产生的 RDD 的分区数与原 RDD 的分区数一
 *               致。中间存在 shuffle 的过程
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark12_SortBy {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子
    val rdd = sc.makeRDD(List(4, 1, 2, 3, 5, 6), 2)
    // 分区数量不变
    // 底层有shuffle操作
    val newRDD = rdd.sortBy(num => num)
    newRDD.saveAsTextFile("output")
    sc.stop()

  }
}
