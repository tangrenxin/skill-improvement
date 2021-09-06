package sql.a.dataframe.and.dataset

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
 * @Description: DataFrame 与 RDD的相互转化
 * @Author: tangrenxin
 * @Date: 2021/9/6 02:04
 */
object Spark02_DataFrame_RDD_Transform {
  def main(args: Array[String]): Unit = {
    // TODO 创建SparkSQL运行环境
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSQL")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
    // TODO 使用DSL语法，需要引入 spark.implicits._ 包
    import spark.implicits._
    // TODO 创建DF spark.read.(csv format jdbc json load option options orc parquet schema table text textFile)
    val df = spark.read.json("datas/user.json")
    // TODO DF 转 RDD
    // 此时得到的 RDD 存储类型为 Row
    val rdd = df.rdd
    // TODO RDD 转 DF
    var rddData = spark.sparkContext.makeRDD(List(("zhangsan", 30), ("lisi", 40)))
    // 方式一: 添加列名的方式
    val df1 = rddData.toDF("name", "age")
    df1.show
    // 方式二: 样例类方式
    val df2 = rddData.map(t => User(t._1, t._2)).toDF()
    df2.show
    spark.stop()
  }

  case class User(
                   name: String,
                   age: Long
                 )

}
