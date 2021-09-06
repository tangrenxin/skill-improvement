package core.c.rdd.b.operator.a.transform.a.single.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: Coalesce
 *               缩减分区
 *               根据数据量缩减分区，用于大数据集过滤后，提高小数据集的执行效率
 *               当 spark 程序中，存在过多的小任务的时候，可以通过 coalesce 方法，收缩合并分区，
 *               减少分区的个数，减小任务调度成本
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark10_Coalesce {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子
    val rdd = sc.makeRDD(List(1, 2, 3, 4, 5, 6), 3)
    // coalesce 方法默认不会将数据打乱重新组合的
    // 这种情况下的缩减分区可能会导致数据不均衡，导致数据倾斜
    // 如果想要让数据均衡，可以进行shuffle处理，放数据打乱重新组合
    //   val newRDD = rdd.coalesce(2)
    val newRDD = rdd.coalesce(2, true)
    newRDD.saveAsTextFile("output")
    sc.stop()
    // 思考一个问题：我想要扩大分区，怎么办？下一个算子

  }
}
