package core.c.rdd.b.operator.a.transform.c.key.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: key-value类型
 *               reduceByKey
 *               将数据源的数据根据 key 对 value 进行分组
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark16_GroupByKey {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子
    val rdd = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("a", 3), ("b", 4),
    ))
    // groupByKey：将数据源中的数据，相同key的数据分在一个组中，形成一个对偶元组
    //            元组中的第一个元素就是key，
    //            元组中的第二个元素就是相同key的value的集合
    val groupRDD = rdd.groupByKey()

    // (a,CompactBuffer(1, 2, 3))
    //(b,CompactBuffer(4))
    groupRDD.collect().foreach(println)

    // 它与 groupBy 的区别
    val value = rdd.groupBy(_._1)
    // (a,CompactBuffer((a,1), (a,2), (a,3)))
    //(b,CompactBuffer((b,4)))
    value.collect().foreach(println)

    /**
     * groupBy 和 groupByKey 的区别是什么？
     * 从结果看出：
     * 1.从按哪一个key来做分组
     *    groupByKey() 是确定的（如果有多个key怎么办？看下一个例子）
     *    groupBy() 按哪个key来做分组是不确定的
     * 2.从返回值上看
     *  groupByKey()：返回值是 RDD[(String, Iterable[Int])], 返回结果会把value单独拿出来做聚合
     *  groupBy(K)：返回值 RDD[(String, Iterable[(String,Int)])],，返回结果不会把value单独拿出来做聚合，
     *              按整体来进行分组
     */
    // groupByKey 的key是确定的，而groupBy 的key是不确定的

    sc.stop()

  }
}
