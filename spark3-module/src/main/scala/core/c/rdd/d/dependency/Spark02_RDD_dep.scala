package core.c.rdd.d.dependency

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: 依赖关系
 * @Author: tangrenxin
 * @Date: 2021/3/10 18:47
 */
object Spark02_RDD_dep {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("TRXTest")
    sparkConf.set("spark.default.parallelism", "3")
    val sc = new SparkContext(sparkConf) //环境对象
    val lines = sc.textFile("datas/word.txt")
    // 打印依赖关系
    println(lines.dependencies)
    println("**********************")
    val words = lines.flatMap(_.split(" "))
    println("**********************")
    val wordToOne = words.map(word => (word, 1))//.repartition(6)
    println(wordToOne.getNumPartitions)
    println(wordToOne.dependencies)
    println("**********************")
    val wordToSum = wordToOne.reduceByKey(_ + _)
    println(wordToSum.dependencies)
    println("**********************")
    val array = wordToSum.collect() // 收集
    array.foreach(println)
    sc.stop()

    /**
     * 运行结果：
     * List(org.apache.spark.OneToOneDependency@4745e9c)
     * **********************
     * List(org.apache.spark.OneToOneDependency@1981d861)
     * **********************
     * List(org.apache.spark.OneToOneDependency@6b1dc20f)
     * **********************
     * List(org.apache.spark.ShuffleDependency@57545c3f)
     * **********************
     * (scala,1)
     * (spark,1)
     * (hello,2)
     */
  }
}
