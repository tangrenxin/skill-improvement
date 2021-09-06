package sql.a.dataframe.and.dataset

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

/**
 * @Description: DataSet 基本操作
 * @Author: tangrenxin
 * @Date: 2021/9/6 02:04
 */
object Spark03_DataSet {
  def main(args: Array[String]): Unit = {
    // TODO 创建SparkSQL运行环境
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSQL")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
    // TODO 使用DSL语法，需要引入 spark.implicits._ 包
    import spark.implicits._
    // TODO 创建 DataSet
    // 1.使用样例类序列创建
    val caseClassDS = Seq(User("zhangsan", 2),User("lisi", 30)).toDS()
    // 2.使用基本类型的序列创建
    val ds = Seq(1, 2, 3, 4, 5).toDS
    // 注意：在实际使用的时候，很少用到把序列转换成DataSet，更多的是通过RDD来得到DataSet
    // TODO 查询 DataSet 的数据
    caseClassDS.select("age") // 不可以对列进行计算 df.select("age" + 1) <=> df.select("age1")
    caseClassDS.select($"age" + 1) // 可以对列进行计算
    caseClassDS.select('age + 1) // 可以对列进行计算
    caseClassDS.select(caseClassDS("age") + 1).show() // 可以对列进行计算
    // TODO DataSet 可以使用功能性的转换（操作 map、flatMap、filter等等）
    val value = caseClassDS.map(u => u.name)
    value.show()
    spark.stop()
  }

  case class User(
                   name: String,
                   age: Long
                 )

}
