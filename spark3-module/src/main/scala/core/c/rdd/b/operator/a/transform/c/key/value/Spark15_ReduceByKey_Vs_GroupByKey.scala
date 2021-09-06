package core.c.rdd.b.operator.a.transform.c.key.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: key-value类型
 *               reduceByKey 跟 groupByKey 区别演示
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark15_ReduceByKey_Vs_GroupByKey {

  //  def main(args: Array[String]): Unit = {
  //    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
  //    val sc = new SparkContext(sparkConf)
  //    // 获取 RDD
  //    val rdd = sc.makeRDD(List(("a", 1), ("a", 1), ("a", 1), ("b", 1)))
  //    // 指定计算公式为 x+y
  //    val reduceRDD = rdd.reduceByKey((x,y) => x + y)
  //    reduceRDD.collect().foreach(println)
  //    sc.stop()
  //    /**
  //     * 运行结果：
  //     * (a,3)
  //     * (b,1)
  //     */
  //  }


  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)
    // 获取 RDD
    val rdd = sc.makeRDD(List(("a", 1), ("a", 1), ("a", 1), ("b", 1)))
    val reduceRDD = rdd.groupByKey().map {
      case (word, iter) => {
        (word, iter.size)
      }
    }
    reduceRDD.collect().foreach(println)
    sc.stop()

    /**
     * 运行结果：
     * (a,3)
     * (b,1)
     */
  }
}
