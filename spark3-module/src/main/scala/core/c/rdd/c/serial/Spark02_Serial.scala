package core.c.rdd.c.serial

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: 序列化
 * @Author: tangrenxin
 * @Date: 2021/3/10 17:46
 */
object Spark02_Serial {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("serial")
    val sc = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(List("hello world", "hello spark", "hive", "tangrenxin"))

    val search = new Search("h")
    //    search.getMatch1(rdd).collect().foreach(println)
    search.getMatch2(rdd).collect().foreach(println)
    sc.stop()
  }

  // 查询对象 从数据源中查询指定规则的数据
  // scala 中，类的构造参数，其实是类的属性，反编译看到 query用的是this.query
  // 构造参数需要进行闭包检测，其实就等同于类进行比较检测
  class Search(query: String) {
    //  class Search(query: String) extends Serializable {
    def isMatch(s: String): Boolean = {
      s.contains(query)
    }

    // 函数序列化案例
    def getMatch1(rdd: RDD[String]): RDD[String] = {
      rdd.filter(isMatch)
    }

    // 属性序列化案例
    def getMatch2(rdd: RDD[String]): RDD[String] = {
      // 这个方式也可以解决序列化的问题
      // 我们知道，rdd算子以外的代码是在Driver端执行的，这里的val s = query 就是在Driver端执行
      // 在看 变量 s 是String类型的，String类型的变量本身就实现了序列化：public final class String implements java.io.Serializable
      // 所以 变量 s 在分布式情况下是可以被序列化的
      val s = query
      // 此时，rdd算子 在executor端执行，用到已经序列化的变量s，自然就不会报序列化的问题
      rdd.filter(x => x.contains(s))
      // 直接使用query，这是scala类的构造参数，其实就是类的属性，在使用query的时候，其实是this.query
      // 这里的this就是search类本身，如果Search没有实现序列化接口，就会报序列化异常
      rdd.filter(x => x.contains(query))
    }
  }

}
