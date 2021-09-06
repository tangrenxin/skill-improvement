package core.c.rdd.b.operator.a.transform.c.key.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: key-value类型
 *               reduceByKey
 *               可以将数据按照相同的 Key 对 Value 进行聚合
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark15_ReduceByKey {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子
    val rdd = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("a", 3), ("b", 4),
    ))

    // 相同的key分在一个组中，并将他们的value进行聚合
    // scala语言中一般的聚合操作都是两两聚合，spark基于scala开发的，所以它的聚合也是两两聚合
    // [1,2,3]
    // [3,3]
    // [6]
    val reduceRDD = rdd.reduceByKey((x: Int, y: Int) => {
      println(s"x=${x},y=${y}")
      x + y
    })
    /**
     * 既然聚合操作都是两两聚合，那么("b", 4) 只有一个key 为 b的值怎么办：
     * x=1,y=2
     * x=3,y=3
     * (a,6)
     * (b,4)
     * 可以看出 a有三个值，进行了两次计算，b只有一个值，没有进行计算
     * 所以得到结论：reduceByKey 中，如果key的数据只有一个，是不会参与运算的
     */
    reduceRDD.collect().foreach(println)
    sc.stop()

  }
}
