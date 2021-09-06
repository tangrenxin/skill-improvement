package core.c.rdd.e.persist

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: 持久化
 *               cache persist checkpoint的区别是什么
 * @Author: tangrenxin
 * @Date: 2021/3/10 20:37
 */
object Spark06_RDD_Persist_Differentiation {

  def main(args: Array[String]): Unit = {
    /**
     * cache：
     *    将数据临时存储在内存中进行数据重用
     *    会在血缘关系中添加新的依赖，一旦出现问题，可以重头读取数据
     * persist：
     *    将数据临时存储在磁盘文件中进行数据重用
     *    因为涉及到磁盘IO，性能较低，但是数据安全
     *    如果作业执行完毕，临时保存的数据文件会丢失
     * checkpoint：
     *    将数据长久的保存在磁盘文件中进行数据重用
     *    因为涉及到磁盘IO，性能较低，但是数据安全
     *    为了保证数据安全，所以一般情况下，会独立执行作业，即当action算子被触发时，checkpoint会创建一个新的作业
     *    如上一个代码，运行结果，@@@@@@@@@@@出现了8次
     *    说明调用检查点的rdd以前的流程都会重新执行一遍，所以效率比更低
     *    为了能够提高效率，【一般情况下，是需要和cache联合使用的】
     *    执行过程中，会切断血缘关系，重新建立新的血缘关系，
     *    相当于将一个作业的数据源，由原文件切到检查点落盘的文件
     *    下次重读数据时直接从checkpoint文件中读取
     *    checkpoint等同于改变数据源
     */
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("persist")
    val sc = new SparkContext(sparkConf)
        sc.setCheckpointDir("cp")
    val list = List("hello spark", "hello scala")
    val rdd = sc.makeRDD(list)
    val flatRDD = rdd.flatMap(_.split(" "))
    val mapRDD = flatRDD.map(
      word => {
        (word, 1)
      })
//    mapRDD.cache()
    mapRDD.checkpoint()
    // 打印血缘
    println(mapRDD.toDebugString)
    val reduceRDD = mapRDD.reduceByKey(_ + _)
    reduceRDD.collect().foreach(println)
    println("********************************")
    println(mapRDD.toDebugString)
    sc.stop()
  }
}
