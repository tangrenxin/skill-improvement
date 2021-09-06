package core.c.rdd.b.operator.a.transform.c.key.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: key-value类型
 *               aggregateByKey
 *               将数据根据不同的规则进行分区内计算和分区间计算
 *               之前做过一个测试：计算所有分区最大值求和（分区内取最大值，分区间最大值求和） 使用的是glom算子
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark17_AggregateByKey {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子
    val rdd = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("a", 3), ("a", 4),
    ), 2)
    // 分区内求最大值，分区间求和
    // 分区0(a,[1,2])   分区1(a,[3,4])
    // 求最大值 (a,2)    (a,4)
    // 求和     (a,6)

    //aggregateByKey存在柯里化函数，有两个参数列表
    // 第一个参数列表,需要传递一个参数，表示初始值，因为需要跟分区内的第一个数据计算
    //      主要用于当碰见第一个key的时候，和value进行分区内计算
    // 第二个参数列表需要传递两个参数
    //      第一个参数表示分区内计算规则
    //      第二个参数表示分区间计算规则

    // math.min(x,y)
    // math.max(x,y)
    val value = rdd.aggregateByKey(0)(
      (x, y) => math.max(x, y),
      (x, y) => x + y
    )
    value.collect().foreach(println)
    sc.stop()

    /**
     * 思考一个问题：分区内计算规则和分区间计算规则相同怎么办？看下一个代码
     */

  }
}
