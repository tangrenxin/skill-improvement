package core.c.rdd.b.operator.a.transform.a.single.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: 使用 flatMap 切割单词
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark04_FlatMap_Test1 {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子 flatMap
    // 将 List(List(1, 2),List(3,4)) 进行扁平化操作
    val rdd = sc.makeRDD(List(
      "hello world", "hello spark"
    ))

//    rdd.collect().foreach(println)

    val flatMapRDD = rdd.flatMap(
      // "hello world"
      str => {
        str.split(" ") // 返回的是一个可迭代的集合
      }
    )
    flatMapRDD.collect().foreach(println)

    sc.stop()

  }
}
