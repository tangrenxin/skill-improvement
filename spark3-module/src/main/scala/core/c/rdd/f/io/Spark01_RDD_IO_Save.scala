package core.c.rdd.f.io

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: 保存数据
 * @Author: tangrenxin
 * @Date: 2021/3/10 22:41
 */
object Spark01_RDD_IO_Save {
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
    rdd.saveAsSequenceFile("output3")
    sc.stop()
  }
}
