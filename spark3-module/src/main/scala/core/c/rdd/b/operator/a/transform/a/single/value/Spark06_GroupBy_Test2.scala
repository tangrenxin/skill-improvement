package core.c.rdd.b.operator.a.transform.a.single.value

import java.text.SimpleDateFormat

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: groupBy
 *               小功能：从服务器日志数据 apache.log 中获取每个时间段访问量。
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark06_GroupBy_Test2 {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子 groupBy
    val rdd = sc.textFile("datas/apache.log")
    // 得到分组后的迭代器
    val timeRDD = rdd.map(
      line => {
        val datas = line.split(" ")
        val time = datas(3)
        // 获取小时 方式一：
        //     time.split(":")(1)
        // 获取小时 方式二：
        val sdf = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss")
        val date = sdf.parse(time)
        val hour = date.getHours()
        // 获取小时 方式三：
        val sdf1 = new SimpleDateFormat("HH")
        val hour1 = sdf1.format(date)
        // 返回元组，某个小时出现一次
        (hour1, 1)
      }
    ).groupBy(_._1)

    timeRDD.map {
      case (hour, iter) => {
        // 迭代器的长度即为 请求的次数
        (hour, iter.size)
      }
    }.collect().foreach(println)
    sc.stop()

  }
}
