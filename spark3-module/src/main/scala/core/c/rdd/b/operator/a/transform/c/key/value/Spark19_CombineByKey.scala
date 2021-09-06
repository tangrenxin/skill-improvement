package core.c.rdd.b.operator.a.transform.c.key.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: key-value类型
 *               CombineByKey
 *               最通用的对 key-value 型 rdd 进行聚集操作的聚集函数（aggregation function）。类似于
 *               aggregate()，combineByKey()允许用户返回值的类型与输入不一致。
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark19_CombineByKey {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子
    val rdd = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("b", 3), ("b", 4), ("b", 5), ("a", 6)
    ), 2)

    // 获取相同key的数据的平均值
    // combineByKey 方法需要三个参数
    // 第一个参数表示：将相同key的第一个数据进行结构的转换，实现操作
    // 第二个参数表示：分区内的计算规则
    // 第三个参数表示：分区间的计算规则
    val newRDD = rdd.combineByKey(
      v  => (v,1),// 动态决定返回值类型
      (t:(Int,Int), v) => {
        // 分区内计算：value 求和，出现的次数加 1
        (t._1 + v, t._2 + 1)
      },
      // 分区间相加
      (t1:(Int,Int), t2:(Int,Int)) => {
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
