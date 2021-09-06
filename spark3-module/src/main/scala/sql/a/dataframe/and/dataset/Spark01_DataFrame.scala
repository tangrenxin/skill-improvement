package sql.a.dataframe.and.dataset

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
 * @Description: DataFrame 基本操作
 * @Author: tangrenxin
 * @Date: 2021/9/6 02:04
 */
object Spark01_DataFrame {
  def main(args: Array[String]): Unit = {
    // TODO 创建SparkSQL运行环境
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSQL")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
    // TODO 使用DSL语法，需要引入 spark.implicits._ 包
    import spark.implicits._
    // TODO 创建DF spark.read.(csv format jdbc json load option options orc parquet schema table text textFile)
    val df = spark.read.json("datas/user.json")
    // TODO 使用DSL语法查询数据的方式：
    df.select("age") // 不可以对列进行计算 df.select("age" + 1) <=> df.select("age1")
    df.select($"age" + 1) // 可以对列进行计算
    df.select('age + 1) // 可以对列进行计算
    df.select(df("age") + 1) // 可以对列进行计算
    // TODO 查看 DataFrame 的 Schema 信息
    /**
     * root
     * |-- age: long (nullable = true)
     * |-- name: string (nullable = true)
     */
    df.printSchema
    // TODO 获取DF的所有列名
    val columns = df.columns
    columns.foreach(s => print(s + ",")) // age,name,

    // TODO 筛选出age>30的数据
    df.filter($"age" > 30).show
    df.filter('age > 30).show
    df.filter(df("age") > 30).show
    spark.stop()
  }

}
