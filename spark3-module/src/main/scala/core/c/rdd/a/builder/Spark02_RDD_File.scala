package core.c.rdd.a.builder

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/3/7 20:29
 */
object Spark02_RDD_File {

  def main(args: Array[String]): Unit = {
    // TODO 创建 环境 [*] 表示当前系统的最大可用核数,如果不写就是单线程，单核
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)

    // TODO 创建 RDD
    // 从文件中创建RDD 将文件中的数据作为我们处理的数据源
    // path路径默认以当前环境的根路径为基准。可以写绝对路径，也可以写相对路径
    //    sc.textFile("/Users/tangrenxin/xiaomiADProject/skill-improvement/datas/2.txt")
    //    val rdd = sc.textFile("datas/1.txt")
    // path 可以是文集收纳的具体路径，也可以是目录名称
    //    val rdd = sc.textFile("datas")
    // path 路径还可以使用通配符
    val rdd = sc.textFile("datas/1*.txt")
    // path 路径还可以是分布式存储系统路径：HDFS
    //    val rdd = sc.textFile("hdfs://xxxxx")
    rdd.collect() // 只有触发collect方法才会执行应用程序
      .foreach(println)

    // TODO 关闭 环境
    sc.stop()
  }
}
