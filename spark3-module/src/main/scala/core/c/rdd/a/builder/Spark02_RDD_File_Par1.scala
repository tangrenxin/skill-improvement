package core.c.rdd.a.builder

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: Spark的数据分区是如何分配的
 * @Author: tangrenxin
 * @Date: 2021/3/7 20:29
 */
object Spark02_RDD_File_Par1 {

  def main(args: Array[String]): Unit = {
    // TODO 创建 环境 [*] 表示当前系统的最大可用核数,如果不写就是单线程，单核
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)

    // TODO 创建 RDD
    // 数据分区的分配
    // 1.数据以行为单位进行读取
    //    spark读取文件，采用的是hadoop的方式读取，所以一行一行读取，和字节数没有关系
    // 2.数据读取时，以偏移量为单位，偏移量不会被重复读取
    /**
     * @:表示特殊字符：如 换行符
     * 1@@ => 偏移量：012
     * 2@@ => 345
     * 3   => 6
     */
    // 3.数据分区的偏移量范围怎么计算：
    val rdd = sc.textFile("datas/1.txt")

    rdd.saveAsTextFile("output")

    // TODO 关闭 环境
    sc.stop()
  }
}
