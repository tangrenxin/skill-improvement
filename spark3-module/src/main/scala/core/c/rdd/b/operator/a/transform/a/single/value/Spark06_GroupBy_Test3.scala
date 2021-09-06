package core.c.rdd.b.operator.a.transform.a.single.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: groupBy
 *               将数据根据指定的规则进行分组, 分区默认不变，但是数据会被打乱重新组合，我们将这样
 *               的操作称之为 shuffle。极限情况下，数据可能被分在同一个分区中
 *               注意：一个组的数据在一个分区中，但是并不是说一个分区中只有一个组
 *
 *               小功能：WordCount。
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark06_GroupBy_Test3 {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子 groupBy
    val rdd = sc.textFile("datas/1*.txt")
    val words = rdd.flatMap(line => line.split(" "))
    val wordRDD = words.groupBy(word => word)
    wordRDD.collect().foreach(println)

    val wordCount = wordRDD.map {
      case (word, iter) => {
        (word, iter.size)
      }
    }
    println("==============")
    wordCount.collect().foreach(println)

    sc.stop()

  }
}
