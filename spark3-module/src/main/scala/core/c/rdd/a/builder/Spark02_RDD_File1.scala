package core.c.rdd.a.builder

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: wholeTextFiles 读取文件 读取的结果表示为元组，第一个元素表示文件路径，第二个元素表示文件内容
 * @Author: tangrenxin
 * @Date: 2021/3/7 20:29
 */
object Spark02_RDD_File1 {

  def main(args: Array[String]): Unit = {
    // TODO 创建 环境 [*] 表示当前系统的最大可用核数,如果不写就是单线程，单核
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)

    // TODO 创建 RDD
    // 从文件中创建RDD 将文件中的数据作为我们处理的数据源
    // textFile: 以行为单位来读取数据，读取的数据都是字符串
    // wholeTextFiles : 以文件为单位读取数据
    //    读取的结果表示为元组，第一个元素表示文件路径，第二个元素表示文件内容
    val rdd = sc.wholeTextFiles("datas")
    rdd.collect().foreach(println)

    // 结果：
    // (file:/Users/tangrenxin/xiaomiADProject/skill-improvement/datas/11.txt,hello hive
    //hello lll)
    //(file:/Users/tangrenxin/xiaomiADProject/skill-improvement/datas/2.txt,hello aaa
    //hello aaa
    //hello ddd
    //hello fff)
    //(file:/Users/tangrenxin/xiaomiADProject/skill-improvement/datas/1.txt,hello world
    //hello spark)

    // TODO 关闭 环境
    sc.stop()
  }
}
