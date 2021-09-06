package core.c.rdd.b.operator.a.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: 案例实操 看视频前自己编写
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark24_RDD_Req_self {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 读取数据
    val file = sc.textFile("datas/agent.log")
    // 提取需要的数据 得到((省份,广告),次数1)
    val rawData = file.map(
      line => {
        val array = line.split(" ")
        ((array(1), array(4)), 1)
      }
    )
    // 对 ((省份,广告),次数1) 进行聚合，key：(省份,广告) value：次数1 得到((省份,广告),次数n)
    val kVRDD = rawData.reduceByKey(_ + _)

    // 提取 省份 得到 (省份,(广告,次数))
    val pAdWithClick = kVRDD.map {
      case ((p, ad), click) => {
        (p, (ad, click))
      }
    }
    // 按照省份分组
    val value = pAdWithClick.groupByKey()

    val res = value.map {
      case (p, iter: Iterable[(String, Int)]) => {
        (p, iter.toList.sortBy(_._2).reverse.take(3))
      }
    }
    res.collect().foreach(println)
    sc.stop()

  }
}
