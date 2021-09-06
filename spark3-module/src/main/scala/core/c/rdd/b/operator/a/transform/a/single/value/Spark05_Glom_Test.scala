package core.c.rdd.b.operator.a.transform.a.single.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: glom
 *               将同一个分区的数据直接转换为相同类型的内存数组进行处理，分区不变
 *
 *               小功能：计算所有分区最大值求和（分区内取最大值，分区间最大值求和）
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark05_Glom_Test {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子 glom
    // 计算所有分区最大值求和（分区内取最大值，分区间最大值求和）
    val rdd = sc.makeRDD(List(1, 2, 3, 4), 2)

    // 将各个分区的数据转化成相同数据类型的数组
    // [1，2] ,[ 3,4]
    // [2],[4]
    // [6]
    // 将一个分区的数据当做数组返回
    val glomRDD = rdd.glom()
    // 每个分区的最大值
    val maxValueRDD = glomRDD.map(array => array.max)

    // 求和方法1：
    //    val res = maxValueRDD.reduce((x,y)=>x+y)
    // 求和方法2：
    val res = maxValueRDD.collect().sum

    println("计算结果：" + res)

    sc.stop()

  }
}
