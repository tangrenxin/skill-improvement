package core.c.rdd.d.dependency

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: 血缘关系
 * @Author: tangrenxin
 * @Date: 2021/3/10 18:47
 */
object Spark01_RDD_dep {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local").setAppName("TRXTest")
    val sc = new SparkContext(sparkConf) //环境对象
    val lines = sc.textFile("datas/word.txt")
    // 打印血缘关系
    println("RDD4的依赖关系：")
    println(lines.toDebugString)
    println("**********************")
    val words = lines.flatMap(_.split(" "))
    println("RDD3的依赖关系：")
    println(words.toDebugString)
    println("**********************")
    val wordToOne = words.map(word => (word, 1))
    println("RDD2的依赖关系：")
    println(wordToOne.toDebugString)
    println("**********************")
    val wordToSum = wordToOne.reduceByKey(_ + _)
    println("RDD1的依赖关系：")
    println(wordToSum.toDebugString)
    println("**********************")
    val array = wordToSum.collect() // 收集
    array.foreach(println)
    sc.stop()

    /**
     * 运行结果：
     * (1) datas/word.txt MapPartitionsRDD[1] at textFile at Spark01_RDD_dep.scala:15 []
     * |  datas/word.txt HadoopRDD[0] at textFile at Spark01_RDD_dep.scala:15 []
     * **********************
     * (1) MapPartitionsRDD[2] at flatMap at Spark01_RDD_dep.scala:19 []
     * |  datas/word.txt MapPartitionsRDD[1] at textFile at Spark01_RDD_dep.scala:15 []
     * |  datas/word.txt HadoopRDD[0] at textFile at Spark01_RDD_dep.scala:15 []
     * **********************
     * (1) MapPartitionsRDD[3] at map at Spark01_RDD_dep.scala:22 []
     * |  MapPartitionsRDD[2] at flatMap at Spark01_RDD_dep.scala:19 []
     * |  datas/word.txt MapPartitionsRDD[1] at textFile at Spark01_RDD_dep.scala:15 []
     * |  datas/word.txt HadoopRDD[0] at textFile at Spark01_RDD_dep.scala:15 []
     * **********************
     * (1) ShuffledRDD[4] at reduceByKey at Spark01_RDD_dep.scala:25 []
     * +-(1) MapPartitionsRDD[3] at map at Spark01_RDD_dep.scala:22 [] // 这里的 +- 表示遇到Shuffle操作,(1)表示分区
     * |  MapPartitionsRDD[2] at flatMap at Spark01_RDD_dep.scala:19 []
     * |  datas/word.txt MapPartitionsRDD[1] at textFile at Spark01_RDD_dep.scala:15 []
     * |  datas/word.txt HadoopRDD[0] at textFile at Spark01_RDD_dep.scala:15 []
     * **********************
     * (scala,1)
     * (spark,1)
     * (hello,2)
     */
  }
}
