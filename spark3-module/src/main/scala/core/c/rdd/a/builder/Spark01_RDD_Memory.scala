package core.c.rdd.a.builder

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: 从内存中创建rdd
 * @Author: tangrenxin
 * @Date: 2021/9/3 16:02
 */
object Spark01_RDD_Memory {


  def main(args: Array[String]): Unit = {

    // TODO 创建 环境 [*] 表示当前系统的最大可用核数,如果不写就是单线程，单核
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("create RDD")
    val sc = new SparkContext(sparkConf)
    // TODO 创建 RDD
    // 从内存中创建RDD 将内存中集合的数据作为我们处理的数据源
    val seq = Seq[Int](1, 2, 3, 4)
    // parallelize 表示并行
    val rdd = sc.parallelize(seq)
    // makeRDD 方法在底层实现时其实就是调用了sc对象的parallelize方法
//    val rdd = sc.makeRDD(seq)
    rdd.collect() // 只有触发collect方法才会执行应用程序
      .foreach(println)

    // TODO 关闭 环境
    sc.stop()
  }

}
