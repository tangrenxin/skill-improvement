package core.c.rdd.b.operator.a.transform.a.single.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: sortBy
 *               排序
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark12_SortBy1 {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子
    val rdd = sc.makeRDD(List(("1", 1), ("11", 2), ("2", 3)), 2)
    // 分区数量不变
    // 底层有shuffle操作
    // 字典续
    //    val newRDD = rdd.sortBy(t => t._1)
    // 转成数字
    //    val newRDD = rdd.sortBy(t => t._1.toInt)
    // sortBy 可以根据指定的规则对数据源中的数据进行排序，默认为升序，
    // 第二个参数可以改变排序的方式， 默认是升序，true：升序 false：降序
    // sortby ，默认情况下不会改变分区数量，但时候中间存在shuffle操作
    val newRDD = rdd.sortBy(t => t._1.toInt, false)

    newRDD.collect().foreach(println)
    sc.stop()

  }
}
