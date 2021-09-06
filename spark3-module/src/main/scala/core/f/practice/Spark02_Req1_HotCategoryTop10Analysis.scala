package core.f.practice

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: 需求 1：Top10 热门品类
 *               第二种实现方式
 *               方式 一 的优化版本
 * @Author: tangrenxin
 * @Date: 2021/3/11 22:41
 */
object Spark02_Req1_HotCategoryTop10Analysis {
  /**
   * 方式一的问题：
   * 1、原始日志数据 actionRDD 重复使用
   * -- 放缓存
   * -- 放checkpoint
   * 2、cogroup 有可能存在 shuffle，性能可能比较低
   * -- 使用 cogroup 是为了将（品类，点击数）、（品类，下单数）、（品类，支付数）=> (品类，（点击数，下单数，支付数）)
   * -- 优化为：
   * -- （品类，点击数）=> (品类,(点击数,0,0))
   * --  union
   * -- （品类，下单数）=> (品类,(0,下单数,0))
   * --  union
   * -- （品类，支付数）=> (品类,(0,0,支付数))
   * --  然后两两聚合，这样就不需要 cogroup 了
   *
   * @param args
   */

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 1.读取原始日志数据
    val actionRDD = sc.textFile("datas/user_visit_action.txt")
    // 2.统计品类的点击数量：（品类ID，点击量）
    val clickActionRDD = actionRDD.filter(
      action => {
        val datas = action.split("_")
        datas(6) != "-1"
      })
    val clickCountRDD = clickActionRDD.map(
      action => {
        val datas = action.split("_")
        (datas(6), 1)
      }).reduceByKey(_ + _)
    // 3.统计品类的下单数量：（品类ID，下单量）
    val orderActionRDD = actionRDD.filter(
      action => {
        val datas = action.split("_")
        datas(8) != "null"
      })
    val orderCountRDD = orderActionRDD.flatMap(
      action => {
        val datas = action.split("_")
        val cid = datas(8)
        val cids = cid.split(",")
        cids.map(id => (id, 1))
      }).reduceByKey(_ + _)
    // 4.统计品类的点支付量：（品类ID，支付量）
    val payActionRDD = actionRDD.filter(
      action => {
        val datas = action.split("_")
        datas(10) != "null"
      })
    val payCountRDD = payActionRDD.flatMap(
      action => {
        val datas = action.split("_")
        val cid = datas(10)
        val cids = cid.split(",")
        cids.map(id => (id, 1))
      }).reduceByKey(_ + _)
    // 5.将品类进行排序，并且取前10名
    val rdd1 = clickCountRDD.map {
      case (cid, cnt) => {
        (cid, (cnt, 0, 0))
      }
    }
    val rdd2 = orderCountRDD.map {
      case (cid, cnt) => {
        (cid, (0, cnt, 0))
      }
    }
    val rdd3 = payCountRDD.map {
      case (cid, cnt) => {
        (cid, (0, 0, cnt))
      }
    }

    // 将三个数据源合并在一起，统一进行聚合计算
    val sourceRDD = rdd1.union(rdd2).union(rdd3)
    // 用reduceByKey 两两聚合
    val analysisRDD = sourceRDD.reduceByKey((t1, t2) => (t1._1 + t2._1, t1._2 + t2._2, t1._3 + t2._3))
    val resultRDD = analysisRDD.sortBy(_._2, false).take(10)
    // 6.将结果采集到控制台打印出来
    resultRDD.foreach(println)
    sc.stop()
  }
}
