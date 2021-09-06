package core.c.rdd.b.operator.a.transform.c.key.value

import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

/**
 * @Description: key-value类型
 *               partitionBy
 *               将数据按照指定 Partitioner 重新进行分区。Spark 默认的分区器是 HashPartitioner
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark14_PartitionBy {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子
    val rdd = sc.makeRDD(List(1, 2, 3, 4))

    // 转换成 key-value 形式的数据结构 才能使用partitionby
    val mapRDD = rdd.map((_, 1))

    /**
     * partitionBy 根据指定的分区规则 对数据进行重分区
     * 注意：这里的 partitionBy 要与之前的coalesce和repartitions区分开
     * coalesce和repartition：描述的是分区数量的变化，是分区数量的改变
     * partitionBy：描述的是 改变数据所在的位置
     *
     * HashPartitioner 是一个分区器
     * Spark 默认的分区器是 HashPartitioner
     */
    mapRDD.partitionBy(new HashPartitioner(3)).saveAsTextFile("output")

    /**
     * 查看 partitionBy 的源码发现，partitionBy 不是 RDD.scala 类的方法而是PairRDDFunctions中的方法
     * RDD => PairRDDFunctions
     * 隐式转换（二次编译）：
     * 当程序编译出现错误是，会尝试在整个作用域范围之内查找转换规则，看看能不能将其转换成特定类型之后让编译通过
     *
     * 那么 RDD 是如何隐式转换成 PairRDDFunctions 的呢？
     * RDD中有：（用implicit修饰的函数叫做隐式函数，将一个类型转换为另外一个类型，然后调用另外一个类型的方法）
     * implicit def rddToPairRDDFunctions[K, V](rdd: RDD[(K, V)])
     * (implicit kt: ClassTag[K], vt: ClassTag[V], ord: Ordering[K] = null): PairRDDFunctions[K, V] = {
     * new PairRDDFunctions(rdd)
     * }
     *
     * 由这段代码可以看出，将RDD转换为PairRDDFunctions类，然后就能使用PairRDDFunctions中的partition()了。
     *
     *
     */

    /**
     * 思考一个问题：如果重分区的分区器和当前 RDD 的分区器一样怎么办？
     *
     * val newRDD = mapRDD.partitionBy(new HashPartitioner(3))
     *     newRDD.partitionBy(new HashPartitioner(3))
     * newRDD 已经使用 HashPartitioner 对数据进行了重分区，再执行newRDD.partitionBy的时候会再重新分区一次吗？
     *
     * 看源码：
     * 首先 ，HashPartitioner 中有个方法：
     * override def equals(other: Any): Boolean = other match {
     * case h: HashPartitioner =>
     *       h.numPartitions == numPartitions
     * case _ =>
     * false
     * }
     *
     * 再看   partitionBy 的实现
     * def partitionBy(partitioner: Partitioner): RDD[(K, V)] = self.withScope {
     * if (keyClass.isArray && partitioner.isInstanceOf[HashPartitioner]) {
     * throw new SparkException("HashPartitioner cannot partition array keys.")
     * }
     * // 这里会判断，你传递的分区器跟自己的分区器是否相等（分区器形同，重分区的数量相同）
     * // 这里的 Scala当中的 == 就是做了非null校验后的equals，
     * // 所以 这里的 == 最终会走到HashPartitioner 的 equals()
     * if (self.partitioner == Some(partitioner)) {
     * // 如果分区器相同，且分区数相同,他什么也不做，直接返回
     * self
     * } else {
     * // 否则 就会产生新的RDD
     * new ShuffledRDD[K, V, V](self, partitioner)
     * }
     * }
     *
     * 所以来看：
     * * override def equals(other: Any): Boolean = other match {
     * // 如果 分区器相同，且分区数相同，返回true
     * * case h: HashPartitioner =>
     * *       h.numPartitions == numPartitions
     * * case _ =>
     * * false
     * * }
     *
     */

    /**
     * 思考一个问题：Spark 还有其他分区器吗？
     *            HashPartitioner extends Partitioner
     *            进入Partitioner 查看有多少子类就行了
     *
     * 思考一个问题：如果想按照自己的方法进行数据分区怎么办？
     *            自己写一个分区器，继承 Partitioner 按照自己的逻辑实现分区规则就行
     */


    sc.stop()


  }
}
