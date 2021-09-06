package core.c.rdd.b.operator.a.transform.a.single.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: Sample
 *               根据指定的规则，从数据集中随机抽取数据
 *               作用：当数据数据倾斜时，如何知道是哪个key导致的数据倾斜呢？使用它
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark08_Sample {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子 Sample
    val rdd = sc.makeRDD(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
    // sample 算子需要传递三个参数
    // 1.第一个表示，抽取数据后是否将数据放回ture（放回），false（丢弃）
    // 2.第二个表示，每条数据被抽取的概率（抽出多少，这是一个double类型的参数,0-1之间，eg：0.3表示抽出30%）
    //    基准值的概念：
    // 3.第三个表示，抽取数据是随机数算法的种子
    //             表示一个种子，根据这个seed随机抽取，一般情况下只用前两个参数就可以，那么这个参数是干嘛的呢，
    //             这个参数一般用于调试，有时候不知道是程序出问题还是数据出了问题，就可以将这个参数设置为定值
    //    如果不传递第三个参数，那么使用当前系统时间
    println(rdd.sample(
      false,
      0.4
      //      1
    ).collect().mkString(","))
    sc.stop()

  }
}
