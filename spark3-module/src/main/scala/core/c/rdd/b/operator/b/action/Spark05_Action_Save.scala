package core.c.rdd.b.operator.b.action

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: 保存数据
 *              save相关算子
 * @Author: tangrenxin
 * @Date: 2021/3/10 22:41
 */
object Spark05_Action_Save {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("persist")
    val sc = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(
      List(
        ("a", 1),
        ("a", 2),
        ("a", 3)
      )
    )
    rdd.saveAsTextFile("output1")
    rdd.saveAsObjectFile("output2")
    // 要求数据的格式必须为K-V类型
    rdd.saveAsSequenceFile("output3")
    sc.stop()
  }
}
