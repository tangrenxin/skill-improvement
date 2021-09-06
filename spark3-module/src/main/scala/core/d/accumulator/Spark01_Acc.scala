package core.d.accumulator

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: 累加器
 * @Author: tangrenxin
 * @Date: 2021/3/11 01:23
 */
object Spark01_Acc {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(List(1, 2, 3, 4))

    // reduce :分区内计算，分区间计算
    // val i :Int = rdd.reduce(_+_)
    // println(i)  // 结果：10

    // 将 rdd中的数进行累加
    var sum = 0
    rdd.foreach(
      num => {
        sum += num;
      }
    )
    println(sum) // 结果：0  为什么？
    sc.stop()
  }
}
