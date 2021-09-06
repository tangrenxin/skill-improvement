package core.c.rdd.b.operator.a.transform.c.key.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: key-value类型
 *               aggregateByKey
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark18_AggregateByKey_Test {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子
    val rdd = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("b", 3), ("b", 4), ("b", 5), ("a", 6)
    ), 2)

    // aggregateByKey 最终的返回数据结果应该和初始值的类型保持一致
    //        val value = rdd.aggregateByKey("")(
    //          (x, y) => x + y,
    //          (x, y) => x + y
    //        )
    //        value.collect().foreach(println)

    /**
     * 结果：
     * (b,345)
     * (a,126)
     * 可以看到变成字符串拼接了
     */

    // 获取相同key的数据的平均值 初始值(0,0)表示数量的总和 和 出现的次数
    val newRDD = rdd.aggregateByKey((0, 0))(
      // 第一次：key:a t表示初始值(0,0) v表示(a,1)的1，分区内第一次计算结果是a,(1,1)
      // 第二次：key:a t表示第一次key为a的结果(1,1) v表示(a,2)的2，分区内第二次计算结果是a,(3,2)
      (t, v) => {
        // 分区内计算：value 求和，出现的次数加 1
        (t._1 + v, t._2 + 1)
      },
      // 分区间相加
      (t1, t2) => {
        (t1._1 + t2._2, t1._2 + t2._2)
      }
    )
    // 得到 全分区（a,(9,3)）,(b,(12,3)) 此时处理 (9,3) 计算平均值
    // mapValues 算子表示 key不变，对value进行转换
    newRDD.mapValues {
      case (num, cnt) => {
        num / cnt
      }
    }.collect().foreach(println)
    sc.stop()

  }
}
