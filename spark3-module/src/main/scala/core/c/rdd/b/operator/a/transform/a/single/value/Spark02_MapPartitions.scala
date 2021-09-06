package core.c.rdd.b.operator.a.transform.a.single.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: mapPartitions
 *               将待处理的数据以分区为单位发送到计算节点进行处理，
 *               这里的处理是指可以进行任意的处理，哪怕是过滤数据。
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark02_MapPartitions {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子 mapPartitions
    val rdd = sc.makeRDD(List(1, 2, 3, 4), 3)
    // mapPartitions：可以以分区为单位进行数据转换操作
    //                但是会将整个分区的数据加载到内存进行引用
    //                这时处理完的数据内存是不会被释放掉的，因为存在对象的引用
    //                在内存较小，数据量较大的场景下，容易出现内存溢出，这种情况下应该用map
    val mpRDD = rdd.mapPartitions(
      // 这里的iter 是一个迭代器，里面包含有一个分区的数据，而map中是line，即一行数据
      // 返回一个迭代器
      iter => {
        println(".>>>>>>>>>")
        val ints = iter.map(_ * 2)
        ints
      }
    )
    // 过滤操作
    val mpRDD2 = rdd.mapPartitions(
      // 这里的iter 是一个迭代器，里面包含有一个分区的数据，而map中是line，即一行数据
      // 返回一个迭代器
      iter => {
        println(".=======")
        val ints = iter.filter(n => n%2==0)
        ints
      }
    )
    mpRDD.collect().foreach(println)
    mpRDD2.collect().foreach(println)

    sc.stop()

  }
}
