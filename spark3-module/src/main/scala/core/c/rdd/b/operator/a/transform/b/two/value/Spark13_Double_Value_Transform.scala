package core.c.rdd.b.operator.a.transform.b.two.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: 双Value 类型的算子
 *              交集、并集、差集、拉链
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark13_Double_Value_Transform {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子
    val rdd1 = sc.makeRDD(List(1, 2, 3, 4))
    val rdd2 = sc.makeRDD(List(3, 4, 5, 6))

    // 交集：
    val rdd3 = rdd1.intersection(rdd2)
    println(rdd3.collect().mkString(","))

    // 并集：
    val rdd4 = rdd1.union(rdd2)
    println(rdd4.collect().mkString(","))

    // 差集：
    val rdd5 = rdd1.subtract(rdd2)
    println(rdd5.collect().mkString(","))

    /**
     * 思考一个问题：如果两个 RDD 数据类型不一致怎么办？ 编译不过
     *
     * 结论：交集并集差集，要求两个rdd的数据类型要一致
     */

    // 拉链：将相同位置的
    val rdd6 = rdd1.zip(rdd2)
    println(rdd6.collect().mkString(","))
    sc.stop()

    /**
     * 思考一个问题：如果两个 RDD 数据类型不一致怎么办？
     *    结论：拉链类型的操作两个数据类型可以不一致
     *
     * 思考一个问题：如果两个 RDD 数据分区不一致怎么办？
     *    结论：拉链类型的操作两个数据的分区要一致
     *
     * 思考一个问题：如果两个 RDD 分区数一致，分区中的数量不一致怎么办？
     *    结论：拉链类型的操作两个数据的分区中的数据数量要一致
     */


  }
}
