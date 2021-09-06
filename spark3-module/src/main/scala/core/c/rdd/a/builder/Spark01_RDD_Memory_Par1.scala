package core.c.rdd.a.builder

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: 从集合中获取数据，数据是如何分配到分区的
 * @Author: tangrenxin
 * @Date: 2021/3/7 20:29
 */
object Spark01_RDD_Memory_Par1 {

  def main(args: Array[String]): Unit = {
    // TODO 创建 环境 [*] 表示当前系统的最大可用核数,如果不写就是单线程，单核
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)

    // TODO 创建 RDD
    val rdd = sc.makeRDD(List(1, 2,3,4,5) , 3)
    // 将处理的数据保存成分区文件，
    rdd.saveAsTextFile("output")

    // TODO 关闭 环境
    sc.stop()
  }
}
