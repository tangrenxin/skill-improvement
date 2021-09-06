package core.c.rdd.b.operator.a.transform.a.single.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: map
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark01_Map {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子 map
    val rdd = sc.makeRDD(List(1, 2, 3, 4))

    // 转换函数
    def mapFunction(num: Int): Int = {
      num * 2
    }

    // 使用转换函数
    //    val mapRDD = rdd.map(mapFunction)
    //  简化版
    //    val mapRDD = rdd.map((num: Int) => {num * 2})
    //  如果函数代码逻辑只有一行，省略{}
    //    val mapRDD = rdd.map((num: Int) => num * 2)
    //  如果函数的类型可以推断出来，类型可以省略
    //    val mapRDD = rdd.map((num) => num * 2)
    //  如果参数列表的参数只有一个，参数列表的括号()可以省略
    //    val mapRDD = rdd.map(num => num * 2)
    //  如果参数在逻辑中只出现一次并且是按照顺序出现，可以使用 _ 代替
    val mapRDD = rdd.map(_ * 2)

    mapRDD.collect().foreach(println)

    sc.stop()

  }
}
