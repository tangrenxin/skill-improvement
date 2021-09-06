package sql.a.dataframe.and.dataset

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
 * @Description: DataSet DataFrame RDD的相互转化
 * @Author: tangrenxin
 * @Date: 2021/9/6 02:04
 */
object Spark04_DataSet_DataFrame_RDD_Transform {
  def main(args: Array[String]): Unit = {
    // TODO 创建SparkSQL运行环境
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSQL")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
    // TODO 使用DSL语法，需要引入 spark.implicits._ 包
    import spark.implicits._
    // TODO 创建DF
    val df = spark.read.json("datas/user.json")

    // TODO 创建DS
    val ds = Seq(User("zhangsan", 2), User("lisi", 30)).toDS()

    // TODO 创建RDD
    var rddData = spark.sparkContext.makeRDD(List(("zhangsan", 30), ("lisi", 40)))

    // RDD 转 DS
    // 够自动将包含有 case 类的RDD 转换成 DataSet，case 类定义了 table 的结构，case 类属性通过反射变成了表的列名。
    val rddToDS = rddData.map(t => User(t._1, t._2)).toDS()

    // DS 转 RDD
    // DataSet 其实也是对 RDD 的封装，所以可以直接获取内部的 RDD,此时返回的类型是样例类
    val rdd = ds.rdd

    // DF 转 DS
    // DataFrame 其实是 DataSet 的特例，所以它们之间是可以互相转换的。
    val dfToDS = df.as[User]

    // DS 转 DF
    val dsToDF = ds.toDF()

    spark.stop()
  }

  case class User(
                   name: String,
                   age: Long
                 )

}
