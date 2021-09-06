package core.c.rdd.b.operator.a.transform.a.single.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: map 的练习例子
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark01_Map_Test {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子 map
    val rdd = sc.textFile("datas/apache.log")

    // 长的字符串转换为短的字符串
    val mapRDD = rdd.map(
      line => {
        // 切分行数据，得到DataSource数组
        val datas = line.split(" ")
        // 提取需要的字段数据
        val str = datas(6)
        str
      }
    )
    mapRDD.collect().foreach(println)


    sc.stop()

  }
}
