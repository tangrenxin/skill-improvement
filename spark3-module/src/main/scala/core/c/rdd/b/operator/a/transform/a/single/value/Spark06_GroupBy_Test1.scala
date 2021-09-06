package core.c.rdd.b.operator.a.transform.a.single.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: groupBy
 *               将数据根据指定的规则进行分组, 分区默认不变，但是数据会被打乱重新组合，我们将这样
 *               的操作称之为 shuffle。极限情况下，数据可能被分在同一个分区中
 *               注意：一个组的数据在一个分区中，但是并不是说一个分区中只有一个组
 *
 *               小功能：将 List("Hello", "hive", "hbase", "Hadoop")根据单词首写字母进行分组。
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark06_GroupBy_Test1 {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子 groupBy
    val rdd = sc.makeRDD(List("Hello", "hive", "hbase", "Hadoop"))

    // groupBy 会将数据源中的每一个数据进行分组判断，根据返回的分组key进行分组
    // 相同的key值的数据会放置在一个组中
    def groupFunction(str: String): Char = {
      str.charAt(0)
    }

    // 按单词的第一个字母分组，相同的手写字母将会放在一起
    // 分组和分区没有必然的关系

    // 函数实现方式：
//    val groupRDD = rdd.groupBy(groupFunction)

    // 非函数实现方式：
    val groupRDD = rdd.groupBy(_.charAt(0))

    groupRDD.collect().foreach(println)

    sc.stop()

  }
}
