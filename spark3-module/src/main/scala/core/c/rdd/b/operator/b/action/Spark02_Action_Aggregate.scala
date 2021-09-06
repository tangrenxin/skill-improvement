package core.c.rdd.b.operator.b.action

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: 行动算子
 *               aggregate
 *               fold
 * @Author: tangrenxin
 * @Date: 2021/3/10 02:33
 */
object Spark02_Action_Aggregate {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(List(1, 2, 3, 4), 2)

    // aggregateByKey:初始值只会参与分区内计算
    // aggregate:初始值会参与分区内计算，并且会参与分区间计算
    //    println(rdd.aggregate(0)(_ + _, _ + _))

    println(rdd.fold(0)(_ + _))
    sc.stop()
  }
}
