package core.f.practice

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: 需求 2：Top10 热门品类中每个品类的 Top10 活跃 Session 统计
 *               在需求一的基础上，增加每个品类用户 session 的点击统计
 * @Author: tangrenxin
 * @Date: 2021/3/11 22:41
 */
object Spark05_Req2_HotCategoryTop10SeesionAnalysis {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    val actionRDD = sc.textFile("datas/user_visit_action.txt")
    actionRDD.cache()
    val top10 = top10Category(actionRDD)
    // 1.过滤原始数据，保留点击和前10品类ID,取出前十品类的数据
    val filterActionRDD = actionRDD.filter(
      action => {
        val datas = action.split("_")
        // 判断是否点击事件
        if (datas(6) != "-1") {
          // 判断 品类id是否是前十的品类id
          top10.contains(datas(6))
        } else {
          false
        }
      }
    )
    // 2.根据品类id和sessionid进行点击量的统计
    val reduceRDD = filterActionRDD.map(
      action => {
        val datas = action.split("_")
        ((datas(6), datas(2)), 1)
      }
    ).reduceByKey(_ + _)
    // 3.将统计的结果进行结构转换
    // ((品类ID,sessionID),sum) => (品类ID,(sessionID,sum))
    val mapRDD = reduceRDD.map {
      case ((cid, sid), sum) => {
        (cid, (sid, sum))
      }
    }
    // 4.相同品类进行分组
    val groupRDD = mapRDD.groupByKey()
    // 5.将分组后的数据进行点击量排序，并去top10
    val resultRDD = groupRDD.mapValues(
      iter => {
        iter.toList.sortBy(_._2).reverse.take(10)
        //        iter.toList.sortBy(_._2)(Ordering.Int.reverse).take(10)
      }
    )
    resultRDD.collect().foreach(println)
    sc.stop()
  }

  def top10Category(actionRDD: RDD[String]) = {
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
    flatRDD
    // 3.将相同品类ID的数据进行分组聚合
    //    (品类id,(点击数,下单数,支付数))
    val analysisRDD = flatRDD.reduceByKey((t1, t2) => (t1._1 + t2._1, t1._2 + t2._2, t1._3 + t2._3))
    // 4.排序取top10 只取top10的品类
    val resultRDD = analysisRDD
      .sortBy(_._2, false)
      .take(10)
      .map(_._1)
    resultRDD
  }
}
