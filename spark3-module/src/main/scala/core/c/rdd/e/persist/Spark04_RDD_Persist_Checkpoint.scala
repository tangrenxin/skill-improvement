package core.c.rdd.e.persist

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: 持久化
 *               演示
 * @Author: tangrenxin
 * @Date: 2021/3/10 20:37
 */
object Spark04_RDD_Persist_Checkpoint {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("persist")
    val sc = new SparkContext(sparkConf)
        sc.setCheckpointDir("cp")
    val list = List("hello spark", "hello scala")
    val rdd = sc.makeRDD(list)
    val flatRDD = rdd.flatMap(_.split(" "))
    val mapRDD = flatRDD.map(
      word => {
        // 为了证明 RDD对象被重用时，会重新读取数据，打印标识
        println("@@@@@@@@@@@")
        (word, 1)
      })
    // checkpoint 需要落盘，需要指定检查点保存路径
    // persist 写到磁盘的时候为什么不用配路径，是因为它是临时文件，应用执行完成后会被删除
    // 而checkpoint路径保存的文件，当作业执行完毕后，不会被删除
    // 一般保存路径都是在分布式存储系统中：HDFS
    // 为了保证数据安全，一般情况下，会独立执行作业，
    // 所以这个例子中，println("@@@@@@@@@@@") 会被打印两轮
    mapRDD.checkpoint()
    val reduceRDD = mapRDD.reduceByKey(_ + _)
    reduceRDD.collect().foreach(println)
    println("********************************")
    val groupRDD = mapRDD.groupByKey()
    groupRDD.collect().foreach(println)
    sc.stop()
    /**
     * 运行结果：
     *
     * @@@@@@@@@@@
     * @@@@@@@@@@@
     * @@@@@@@@@@@
     * @@@@@@@@@@@
     * @@@@@@@@@@@
     * @@@@@@@@@@@
     * @@@@@@@@@@@
     * @@@@@@@@@@@
     * (spark,1)
     * (scala,1)
     * (hello,2)
     * ********************************
     * (spark,CompactBuffer(1))
     * (scala,CompactBuffer(1))
     * (hello,CompactBuffer(1, 1))
     */
  }
}
