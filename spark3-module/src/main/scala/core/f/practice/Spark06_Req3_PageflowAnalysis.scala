package core.f.practice

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: 需求 3：页面单跳转化率统计
 *               计算页面单跳转化率，什么是页面单跳转换率，比如一个用户在一次 Session 过程中
 *               访问的页面路径 3,5,7,9,10,21，那么页面 3 跳到页面 5 叫一次单跳，7-9 也叫一次单跳，
 *               那么单跳转化率就是要统计页面点击的概率。
 * @Author: tangrenxin
 * @Date: 2021/3/11 22:41
 */
object Spark06_Req3_PageflowAnalysis {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    val actionRDD = sc.textFile("datas/user_visit_action.txt")
    val actionDataRDD = actionRDD.map(
      action => {
        val datas = action.split("_")
        UserVisitAction(
          datas(0),
          datas(1).toLong,
          datas(2),
          datas(3).toLong,
          datas(4),
          datas(5),
          datas(6).toLong,
          datas(7).toLong,
          datas(8),
          datas(9),
          datas(10),
          datas(11),
          datas(12).toLong
        )
      }
    )
    actionDataRDD.cache()

    // TODO 优化： 对指定的页面连续跳转进行统计
    // 只统计 1-2,2-3,3-4,4-5,5-6,6-7 的页面
    // 计算分母的时候，其实是不包含最后一个的
    val ids = List[Long](1, 2, 3, 4, 5, 6, 7)

    // 供分子计算时过滤使用
    val okFlowIds = ids.zip(ids.tail)

    /**
     * 将两个 RDD 中的元素，以键值对的形式进行合并。其中，键值对中的 Key 为第 1 个 RDD
     * 中的元素，Value 为第 2 个 RDD 中的相同位置的元素。
     * println(okFlowIds)
     * List((1,2), (2,3), (3,4), (4,5), (5,6), (6,7))
     */

    // TODO 计算分母 ：计算每个页面的点击次数
    // 转成 map 方便后面计算时根据pageid提取对应额page pv
    val pageIdToCount = actionDataRDD.filter(
      action => {
        // scala init :除了最后一个
        ids.init.contains(action.page_id)
      }
    ).map(
      action => {
        (action.page_id, 1)
      }
    ).reduceByKey(_ + _).collect().toMap

    // TODO 计算分子
    // 根据用户的sessionid分组
    val sessionRDD = actionDataRDD.groupBy(_.session_id)

    // 分组后根据访问时间进行排序（升序）
    val mvRDD = sessionRDD.mapValues(
      iter => {
        val sortList = iter.toList.sortBy(_.action_time)
        // 得到连续的page id的集合
        val flowIds = sortList.map(_.page_id)
        // 原：[1,2,3,4]
        // 得到：[1-2,2-3,3-4]
        // 生成 (page1,page2)
        // 如何实现
        // 1、scala的划窗：Sliding
        // 2、scala 中的 zip，拉链，跟rdd的zip有点区别
        // 得到 页面连续的操作
        val pageFlowIds = flowIds.zip(flowIds.tail)
        // 将不合法的页面跳转进行过滤
        val filterRDD = pageFlowIds.filter(t => okFlowIds.contains(t))


          // 转成以（page1,page2）为key，1为value，表示page1->page2跳转了一次
        filterRDD.map(t => (t, 1))
      }
    )
    // 不需要sessionID了，并拆开（（page1,page2），1）
    // list => list 相当于把分组后的多个list合并成一个list
    val flatRDD = mvRDD.map(_._2).flatMap(list => list)
    // ((page1,page2),sum)
    val dataRDD = flatRDD.reduceByKey(_ + _)
    // TODO 计算单跳转换率
    // 分子 / 分母
    dataRDD.foreach {
      case ((page1, page2), sum) => {
        // 分母
        val lon = pageIdToCount.getOrElse(page1, 0)
        println(s"页面${page1}跳转到页面${page2}的单跳转换率为：" + (sum.toDouble / lon))
      }
    }

    sc.stop()

  }

  //建一个样例类，方便读取数据
  //用户访问动作表
  case class UserVisitAction(
                              date: String, //用户点击行为的日期
                              user_id: Long, //用户的 ID
                              session_id: String, //Session 的 ID
                              page_id: Long, //某个页面的 ID
                              action_time: String, //动作的时间点
                              search_keyword: String, //用户搜索的关键词
                              click_category_id: Long, //某一个商品品类的 ID
                              click_product_id: Long, //某一个商品的 ID
                              order_category_ids: String, //一次订单中所有品类的 ID 集合
                              order_product_ids: String, //一次订单中所有商品的 ID 集合
                              pay_category_ids: String, //一次支付中所有品类的 ID 集合
                              pay_product_ids: String, //一次支付中所有商品的 ID 集合
                              city_id: Long
                            ) //城市 id
}
