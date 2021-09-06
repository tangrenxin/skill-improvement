package core.c.rdd.a.builder

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: Spark的数据分区是如何分配的
 * @Author: tangrenxin
 * @Date: 2021/3/7 20:29
 */
object Spark02_RDD_File_Par2 {

  def main(args: Array[String]): Unit = {
    // TODO 创建 环境 [*] 表示当前系统的最大可用核数,如果不写就是单线程，单核
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)

    // TODO 创建 RDD
    // 12 byte / 2 = 6byte
    // 14 / 7 = 2（分区）
    /*
    文件数据对应的偏移量
    1234567@@ => 012345678
    89@@      => 9 10 11 12
    0         => 13

    偏移量的范围：
    [0,7]  => 1234567
    [7,12] => 890
     */

    // 如果数据源为多个文件，那么计算分区时以文件为单位进行分区
    val rdd = sc.textFile("datas/word.txt", 2)

    rdd.saveAsTextFile("output")

    // TODO 关闭 环境
    sc.stop()
  }
}
