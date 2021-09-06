package core.c.rdd.b.operator.a.transform.a.single.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: groupBy
 *               将数据根据指定的规则进行分组, 分区默认不变，但是数据会被打乱重新组合，我们将这样
 *               的操作称之为 shuffle。极限情况下，数据可能被分在同一个分区中
 *               注意：一个组的数据在一个分区中，但是并不是说一个分区中只有一个组
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark06_GroupBy {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子 groupBy
    val rdd = sc.makeRDD(List(1, 2, 3, 4), 2)

    // groupBy 会将数据源中的每一个数据进行分组判断，根据返回的分组key进行分组
    // 相同的key值的数据会放置在一个组中
    def groupFunction(num: Int): Int = {
      num % 2
    }
    // 根据数据  判断它在哪一个组当中，所以他是有一个函数的
    val groupRDD = rdd.groupBy(groupFunction)
    groupRDD.collect().foreach(println)

    sc.stop()

  }
}
