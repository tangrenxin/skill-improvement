package sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/3/20 23:19
 */
object Spark01_SparkSql_basic {

  def main(args: Array[String]): Unit = {

    // 创建SparkSQL运行环境
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSQL")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
    // 如果需要RDD与DF或者DS之间相互操作，那么需要引入
    import spark.implicits._
    val df = spark.read.json("datas/user.json").select("name","age")
    // df 转 RDD
    val rdd = df.rdd

    // rdd 转 df 方式一：通过样例类
    val userRDD = rdd.map(row=>User(row.getString(0),row.getLong(1)))
    userRDD.toDF.show

    // rdd 转 df 方式二：指定列名
    val rdd2 = spark.sparkContext.makeRDD(List(("zhangsan",30), ("lisi",40)))
    rdd2.toDF("name","age").show()

    val value = df.as[User]
    value.show()
//    df.show
    spark.stop()
  }

  case class User(
                   name: String,
                   age: Long
                 )

}
