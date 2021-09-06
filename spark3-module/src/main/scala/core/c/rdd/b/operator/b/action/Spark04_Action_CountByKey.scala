package core.c.rdd.b.operator.b.action

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: 行动算子
 *               countByKey
 * @Author: tangrenxin
 * @Date: 2021/3/10 02:33
 */
object Spark04_Action_CountByKey {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    //    val rdd = sc.makeRDD(List(1, 1, 3, 4), 2)

    // 统计数据出现的次数
    //    println(rdd.countByValue())

    val rdd = sc.makeRDD(List(
      ("a",1),("a",2),("a",3)
    ))
    println(rdd.countByKey())

    sc.stop()
  }
}
