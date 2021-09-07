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
//    df.show
    spark.stop()
  }
}
