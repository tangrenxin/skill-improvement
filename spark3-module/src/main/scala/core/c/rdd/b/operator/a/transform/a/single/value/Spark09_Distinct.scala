package core.c.rdd.b.operator.a.transform.a.single.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: distinct
 *               去重数据
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark09_Distinct {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子 Sample
    val rdd = sc.makeRDD(List(1, 2, 3, 4, 1, 2, 3, 4))
    // scala 集合的distinct函数去重的原理是，底层是用hashSet实现的，
    // 那么 RDD 中的去重方式什么呢？
    //源码： case _ => map(x => (x, null)).reduceByKey((x, _) => x, numPartitions).map(_._1)
    // 1. map(x => (x, null)) :
    //  (1,null),(2,null),(3,null),(4,null),(1,null),(2,null),(3,null),(4,null)
    // 2. reduceByKey((x, _) => x, numPartitions)
    // reduceByKey:把相同key的数据放在一起，然后将他们的value进行聚合，最终返回(key,聚合结果)
    // 聚合规则：(x, _) => x 解读：聚合集里只去第一个，其他的不管（对照看两数加和：(x, y) => x+y）
    //  (1) 对于(1,null)(1,null) value 集是：(null,null)
    //  (2) (null,null) => null 然后根据聚合规则(x, _) => x 取出第一值，也就是 null
    //  (3) 得到此次聚合的结果：(1,null)
    //  (4) 如果有更多元组（1,null），用上一次聚合的结果，跟本元组（1,null）进行reduceByKey，重复上述过程
    // 3. map(_._1) :
    // 最终得到去重后的结果 (1,null) => 1 最终得到 key : 1
    // 这就是RDD的distinct的执行原理
    // 思考一个问题：如果不用该算子，你有什么办法实现数据去重？
    // 其实就是将distinct的底层实现搬出来执行就行。看下一个代码
    val res = rdd.distinct()
    res.collect().foreach(println)
    sc.stop()

  }
}
