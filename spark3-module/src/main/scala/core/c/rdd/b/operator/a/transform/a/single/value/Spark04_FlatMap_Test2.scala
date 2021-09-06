package core.c.rdd.b.operator.a.transform.a.single.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: flatMap 将 List(List(1,2),3,List(4,5))进行扁平化操作
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark04_FlatMap_Test2 {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子 flatMap
    // 将  List(List(1,2),3,List(4,5))进行扁平化操作
    // 注意其中有个元素 3 不是list
    val rdd = sc.makeRDD(List(List(1, 2), List(4, 5),3,6))

    // 注意：当数据类型不一样时，对不同的数据类型，进行不同的处理，用到模式匹配
    val flatMapRDD = rdd.flatMap(
      data => {
        data match {
          case list: List[_] => list // 当元素是List时
          case data => List(data) // 当元素是 3(普通数值) 时，封装成只包含一个元素的List返回
        }
      }
    )
    flatMapRDD.collect().foreach(println)

    sc.stop()

  }
}
