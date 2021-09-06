package core.c.rdd.b.operator.a.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: 案例实操
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark24_RDD_Req {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 1、获取原始数据：时间戳，省份，城市，用户，广告
    val dataRdd = sc.textFile("datas/agent.log")
    // 2、将原始数据进行结构转换，得到 ((省份,广告),次数1)
    val mapRDD = dataRdd.map(
      line => {
        val datas = line.split(" ")
        ((datas(1), datas(4)), 1)
      }
    )
    // 3、将转换结构后的数据，进行分组聚合 得到((省份,广告),sum)
    val reduceRDD = mapRDD.reduceByKey(_+_)
    // 4、将聚合的结果进行结构转换 (省份,(广告,sum))
    val newMapRDD = reduceRDD.map {
      case ((prv, ad), sum) => {
        (prv, (ad, sum))
      }
    }
    // 5、将转换结构后的数据根据省份进行分组得到 (省份,[(广告A，sumA),(广告B，sumB)])
    val groupRDD = newMapRDD.groupByKey()
    // 6、将分组后的数据组内排序，取前三名
    val resRDD = groupRDD.mapValues(
      iter => {
        iter.toList.sortBy(_._2).reverse.take(3)
      }
    )

    resRDD.collect().foreach(println)
    sc.stop()

  }
}
