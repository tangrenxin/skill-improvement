package core.f.practice

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: 需求 1：Top10 热门品类
 *               第三种实现方式
 *               一次性统计每个品类点击的次数，下单的次数和支付的次数：
 *               （品类，（点击总数，下单总数，支付总数））
 * @Author: tangrenxin
 * @Date: 2021/3/11 22:41
 */
object Spark03_Req1_HotCategoryTop10Analysis {
  /**
   * 方式二的问题：
   * 1、存在大量的shuffle操作，有大量的 reduceByKey
   * reduceByKey聚合算子，spark会提供优化，本身就有缓存
   * 可以在一开始的时候，直接将数据转成(品类，（点击数，下单数，支付数）)结构
   *
   * @param args
   */

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 1.读取原始日志数据
    val actionRDD = sc.textFile("datas/user_visit_action.txt")
    // 2.将数据转换结构
    //  点击场合：(品类id,(1,0,0))
    //  下单场合：(品类id,(0,1,0))
    //  支付场合：(品类id,(0,0,1))
    val flatRDD = actionRDD.flatMap(
      action => {
        val datas = action.split("_")
        if (datas(6) != "-1") {
          // 点击场合
          List((datas(6), (1, 0, 0)))
        } else if (datas(8) != "null") {
          // 下单场合
          val ids = datas(8).split(",")
          ids.map(id => (id, (0, 1, 0)))
        } else if (datas(10) != "null") {
          // 支付场合
          val ids = datas(10).split(",")
          ids.map(id => (id, (0, 0, 1)))
        } else {
          Nil
        }
      }
    )
    // 3.将相同品类ID的数据进行分组聚合
    //    (品类id,(点击数,下单数,支付数))
    val analysisRDD = flatRDD.reduceByKey((t1, t2) => (t1._1 + t2._1, t1._2 + t2._2, t1._3 + t2._3))
    // 4.排序取top10
    val resultRDD = analysisRDD.sortBy(_._2, false).take(10)
    // 6.将结果采集到控制台打印出来
    resultRDD.foreach(println)
    sc.stop()
  }
}