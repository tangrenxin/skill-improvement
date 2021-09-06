package core.d.accumulator

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: 累加器 的问题
 * @Author: tangrenxin
 * @Date: 2021/3/11 01:23
 */
object Spark03_Acc {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(List(1, 2, 3, 4))

    // 获取系统累加器
    // Spark默认就提供了几个简单的数据聚合累加器
    val sumAcc = sc.longAccumulator("sum")
    //    sc.doubleAccumulator()
    //    sc.collectionAccumulator()
    // 这里换成 map后，累加器输出为 0 为啥？
    val mapRDD = rdd.map(
      num => {
        // 使用累加器
        sumAcc.add(num)
        num
      }
    )

    // 获取累加器的值
    // 少加：转换算子中调用累加器，如果没有行动算子的话，那么不会执行
    // 多加：转换算子中调用累加器，如果没有行动算子的话，那么不会执行
    // 一般情况下，累加器会放置在行动算子中进行操作
    mapRDD.collect()
    mapRDD.collect() // 调用两次action算子  两次累加
    println(sumAcc.value)
    sc.stop()
  }
}
