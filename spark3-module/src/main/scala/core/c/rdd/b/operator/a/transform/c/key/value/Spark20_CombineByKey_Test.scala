package core.c.rdd.b.operator.a.transform.c.key.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: key-value类型
 *               CreduceByKey、foldByKey、aggregateByKey、combineByKey 的区别？
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark20_CombineByKey_Test {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子
    val rdd = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("b", 3), ("b", 4), ("b", 5), ("a", 6)
    ), 2)

    rdd.reduceByKey(_ + _) // WordCount
    rdd.aggregateByKey(0)(_ + _, _ + _) // WordCount
    rdd.foldByKey(0)(_ + _) // WordCount
    rdd.combineByKey(v => v, (x: Int, y) => x + y, (x: Int, y: Int) => x + y) // WordCount
    sc.stop()

    /**
     * 分别查看上面几个算子的源码，看看区别
     * reduceByKey
     * combineByKeyWithClassTag[V](
     *    (v: V) => v, // 第一个值不会参与计算
     *    func, // 表示分区内数据的处理函数
     *    func, // 表示分区内数据的处理函数
     *    partitioner
     * )
     *
     * aggregateByKey
     * combineByKeyWithClassTag[U](
     *    (v: V) => cleanedSeqOp(createZero(), v), // 初始值和第一个key的value进行的分区内数据操作
     *    cleanedSeqOp,// 表示分区内数据的处理函数
     *    combOp,      // 表示分区间数据的处理函数
     * partitioner
     * )
     *
     * foldByKey
     * combineByKeyWithClassTag[V](
     *    (v: V) => cleanedFunc(createZero(), v), // 初始值和第一个key的value进行的分区内数据操作
     *    cleanedFunc, // 表示分区内数据的处理函数
     *    cleanedFunc, // 表示分区间数据的处理函数
     *    partitioner
     * )
     *
     * combineByKey
     * combineByKeyWithClassTag(
     *    createCombiner, // 相同key第一条数据进行处理的函数
     *    mergeValue, // 表示分区内数据的处理函数
     *    mergeCombiners, // 表示分区间数据的处理函数
     *    defaultPartitioner(self)
     * )
     */



  }
}
