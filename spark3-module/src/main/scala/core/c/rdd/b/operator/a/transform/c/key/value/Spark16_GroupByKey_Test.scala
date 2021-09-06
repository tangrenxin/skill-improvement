package core.c.rdd.b.operator.a.transform.c.key.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: key-value类型
 *               reduceByKey
 *               将数据源的数据根据 key 对 value 进行分组
 *
 *               groupByKey() 是确定的（如果有多个key怎么办？） 疑问测试代码
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark16_GroupByKey_Test {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    /**
     * 情景一：如果将rdd改成这样，使用groupByKey()编译会不通过，需要使用groupBy()指定key才行
     * 使用groupBy()运行结果：
     * (二,CompactBuffer((a,二,2)))
     * (一,CompactBuffer((a,一,1)))
     * (三,CompactBuffer((a,三,3)))
     * (四,CompactBuffer((b,四,4)))
     */
    val rdd = sc.makeRDD(List(
      ("a", "一", 1), ("a", "二", 2), ("a", "三", 3), ("b", "四", 4)
    ))

    /**
     * 情景二：如果将人定的改成这样，使用groupByKey()编译通过
     * 使用groupByKey()运行结果：
     * (a,CompactBuffer((一,1), (二,2), (三,3)))
     * (b,CompactBuffer((四,4)))
     */
    //    val rdd = sc.makeRDD(List(
    //      ("a", ("一", 1)), ("a", ("二", 2)), ("a", ("三", 3)), ("b", ("四", 4))
    //    ))
    //    val groupRDD = rdd.groupByKey()
    val groupRDD = rdd.groupBy(_._2)


    groupRDD.collect().foreach(println)
    sc.stop()

  }
}
