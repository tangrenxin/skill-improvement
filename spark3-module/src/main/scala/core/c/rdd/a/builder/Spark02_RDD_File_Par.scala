package core.c.rdd.a.builder

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: 从文件中读取数据，数据是如何分区的
 * @Author: tangrenxin
 * @Date: 2021/3/7 20:29
 */
object Spark02_RDD_File_Par {

  def main(args: Array[String]): Unit = {
    // TODO 创建 环境 [*] 表示当前系统的最大可用核数,如果不写就是单线程，单核
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)

    // TODO 创建 RDD
    // textFile可以将文件作为数据处理的数据源，默认也可以设定分区
    val rdd = sc.textFile("datas/1.txt")

    // 如果不想使用默认的分区数量，可以通过第二个参数指定分区数
    // Spark 读取文件，底层其实使用的就是Hadoop的读取方式
    // 分区数量的计算方式：就是hadoop文件分区的计算方式
    rdd.saveAsTextFile("output")

    // TODO 关闭 环境
    sc.stop()
  }
}
