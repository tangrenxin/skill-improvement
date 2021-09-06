package core.c.rdd.a.builder

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: 并行度
 * @Author: tangrenxin
 * @Date: 2021/9/3 17:05
 */
object Spark01_RDD_Memory_Par {

  def main(args: Array[String]): Unit = {
    // TODO 创建 环境 [*] 表示当前系统的最大可用核数,如果不写就是单线程，单核
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("create RDD")
//    sparkConf.set("spark.default.parallelism", "3")
    val sc = new SparkContext(sparkConf)
    // TODO 创建 RDD
    // 1.makeRDD 可以传递第二个参数，表示分区的数量
    val rdd = sc.makeRDD(List(1, 2, 3, 4,5,6),10)
    // 2.第二个参数可以不传递，会使用默认值 也就是 [*] 不传并行度 结果：mac为4核，有四个并行度
//        val rdd = sc.makeRDD(List(1, 2, 3, 4,5,6))
    // 3.通过 spark.default.parallelism 设置并行度
//    val rdd = sc.makeRDD(List(1, 2))

    // 将处理的数据保存成分区文件，
    rdd.saveAsTextFile("output")

    // TODO 关闭 环境
    sc.stop()
  }

}
