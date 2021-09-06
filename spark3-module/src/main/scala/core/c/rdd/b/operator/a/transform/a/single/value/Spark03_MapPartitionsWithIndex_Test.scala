package core.c.rdd.b.operator.a.transform.a.single.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: mapPartitionsWithIndex
 *               看看数据都在哪个分区里
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark03_MapPartitionsWithIndex_Test {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子 mapPartitions
    // 返回数据和所在的分区
    val rdd = sc.makeRDD(List(1, 2, 3, 4), 2)
    val mpRDD = rdd.mapPartitionsWithIndex(
      (index, iter) => {
        iter.map(
          num => {
            (num,index)
          }
        )
      }
    )
    mpRDD.collect().foreach(println)

    sc.stop()

  }
}
