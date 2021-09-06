package core.c.rdd.b.operator.a.transform.c.key.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: key-value类型
 *               leftOutJoin
 *               类似于 SQL 语句的左外连接
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark22_LeftOutJoin {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子
    val rdd = sc.makeRDD(List(
      ("a", 1), ("c", 2)//, ("b", 3)
    ))
    val rdd2 = sc.makeRDD(List(
      ("b", 4), ("c", 5), ("a", 6)
       ))

    rdd.leftOuterJoin(rdd2).collect().foreach(println)
    println("====================")
    rdd2.leftOuterJoin(rdd).collect().foreach(println)
    println("====================")
    println("====================")
    rdd.rightOuterJoin(rdd2).collect().foreach(println)
    println("====================")
    rdd2.rightOuterJoin(rdd).collect().foreach(println)

    sc.stop()

  }
}
