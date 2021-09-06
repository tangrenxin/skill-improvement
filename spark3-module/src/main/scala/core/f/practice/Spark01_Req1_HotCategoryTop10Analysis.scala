package core.f.practice

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: 需求 1：Top10 热门品类
 *               第一种实现方式
 *               分别统计每个品类点击的次数，下单的次数和支付的次数：
 *               （品类，点击总数）（品类，下单总数）（品类，支付总数）
 * @Author: tangrenxin
 * @Date: 2021/3/11 22:41
 */
object Spark01_Req1_HotCategoryTop10Analysis {

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
    //    排序规则：先按照点击数排名，靠前的就排名高；如果点击数相同，再比较下单数；下单数再相同，就比较支付数。
    //    点击数量排序、下单数量排序、支付数量排序
    //    如何实现上述需求呢？=> 元组排序
    //    元组排序规则：先比较第一个，再比较第二个，再比较第三个，依次类推
    //    所以，只要我们将需要排序的数值按照指定的顺序组成元组，再对元组进行排序，就实现了这个功能
    //    目标元组：（品类ID，（点击数，下单数，支付数））
    // 两个不同的数据源想连在一起 ：join zip leftoutjoin cogroup
    // cogroup = connect +group
    val cogroupRDD =
    clickCountRDD.cogroup(orderCountRDD, payCountRDD)
    val analysisRDD = cogroupRDD.mapValues {
      case (clickIter, orderIter, payIteer) => {
        var clickCnt = 0
        val iter1 = clickIter.iterator
        if (iter1.hasNext) {
          clickCnt = iter1.next()
        }
        var orderCnt = 0
        val iter2 = orderIter.iterator
        if (iter2.hasNext) {
          orderCnt = iter2.next()
        }
        var payCnt = 0
        val iter3 = payIteer.iterator
        if (iter3.hasNext) {
          payCnt = iter3.next()
        }
        (clickCnt, orderCnt, payCnt)
      }
    }
    val resultRDD = analysisRDD.sortBy(_._2, false).take(10)
    // 6.将结果采集到控制台打印出来
    resultRDD.foreach(println)
    sc.stop()
  }
}
