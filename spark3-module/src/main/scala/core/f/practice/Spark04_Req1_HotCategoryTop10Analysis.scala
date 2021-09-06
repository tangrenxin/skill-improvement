package core.f.practice

import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

/**
 * @Description: 需求 1：Top10 热门品类
 *               第四种实现方式
 *               使用累加器的方式聚合数据
 * @Author: tangrenxin
 * @Date: 2021/3/11 22:41
 */
object Spark04_Req1_HotCategoryTop10Analysis {
  /**
   * 方式三的问题：
   * 还是有 shuffle 操作，能不能不用 shuffle 操作就能把功能实现呢
   * -- 累加器
   *
   * @param args
   */

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 1.读取原始日志数据
    val actionRDD = sc.textFile("datas/user_visit_action.txt")
    // 2.注册累加器
    val acc = new HotCategoryAccumulator
    sc.register(acc, "hotCategory")
    actionRDD.foreach(
      action => {
        val datas = action.split("_")
        if (datas(6) != "-1") {
          // 点击场合
          acc.add(datas(6), "click")
        } else if (datas(8) != "null") {
          // 下单场合
          val ids = datas(8).split(",")
          ids.foreach(id => acc.add(id, "order"))
        } else if (datas(10) != "null") {
          // 支付场合
          val ids = datas(10).split(",")
          ids.foreach(id => acc.add(id, "pay"))
        }
      }
    )
    val accVal = acc.value
    // HotCategory 里面已经包含了品类ID
    val categories = accVal.map(_._2)
    // 需要自定义排序
    val result = categories.toList.sortWith(
      (left, right) => {
        if (left.clickCnt > right.clickCnt) {
          true
        } else if (left.clickCnt == right.clickCnt) {
          // 点击数相等，比较下单数
          if (left.orderCnt > right.orderCnt) {
            true
          } else if (left.orderCnt == right.orderCnt) {
            // 下单数相等，比较支付数
            if (left.payCnt > right.payCnt) {
              true
            } else {
              false
            }
          } else {
            false
          }
        } else {
          false
        }
      }
    ).take(10)
    // 6.将结果采集到控制台打印出来
    result.foreach(println)
    sc.stop()
  }

  case class HotCategory(
                          cid: String,
                          var clickCnt: Int,
                          var orderCnt: Int,
                          var payCnt: Int
                        )

  /**
   * 自定义累加器
   * 1.继承AccumulatorV2,定义泛型
   * IN:(品类ID，行为类型)
   * Out: mutable.Map[String,HotCategory]
   */
  class HotCategoryAccumulator extends AccumulatorV2[(String, String), mutable.Map[String, HotCategory]] {
    private val hcMap = mutable.Map[String, HotCategory]()

    override def isZero: Boolean = {
      hcMap.isEmpty
    }

    override def copy(): AccumulatorV2[(String, String), mutable.Map[String, HotCategory]] = {
      new HotCategoryAccumulator()
    }

    override def reset(): Unit = {
      hcMap.clear()
    }

    override def add(v: (String, String)): Unit = {
      val cid = v._1
      val actionType = v._2
      val category = hcMap.getOrElse(cid, HotCategory(cid, 0, 0, 0))
      if (actionType == "click") {
        category.clickCnt += 1
      } else if (actionType == "order") {
        category.orderCnt += 1
      } else if (actionType == "pay") {
        category.payCnt += 1
      }
      hcMap.update(cid, category)
    }

    override def merge(other: AccumulatorV2[(String, String), mutable.Map[String, HotCategory]]): Unit = {
      val map1 = this.hcMap
      val map2 = other.value
      map2.foreach {
        case (cid, hc) => {
          val category = map1.getOrElse(cid, HotCategory(cid, 0, 0, 0))
          category.clickCnt += hc.clickCnt
          category.orderCnt += hc.orderCnt
          category.payCnt += hc.payCnt
          map1.update(cid, category)
        }
      }

    }

    override def value: mutable.Map[String, HotCategory] = hcMap
  }

}
