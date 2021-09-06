package core.c.rdd.b.operator.a.transform.c.key.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: key-value类型
 *               reduceByKey
 *               小功能：使用 reduceByKey 实现 WordCount
 *
 *               可以与groupby算子对应着看下
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark15_ReduceByKey_Test {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子 groupBy
    val rdd = sc.textFile("datas/1*.txt")
    val words = rdd.flatMap(line => line.split(" ")).map(w => (w, 1))
    //    val reduceRDD = words.reduceByKey((x, y) => x + y)
    val reduceRDD = words.reduceByKey(_ + _)
    reduceRDD.collect().foreach(println)
    sc.stop()

  }
}
