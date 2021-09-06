package core.c.rdd.b.operator.a.transform.a.single.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: Sample
 *               根据指定的规则，从数据集中随机抽取数据
 *               模拟发生数据倾斜时，使用sample算子找出导致数据倾斜的key
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark08_Sample_Test {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    val list = List("A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A",
      "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A",
      "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A",
      "B", "B", "B", "B", "B", "B", "B", "B", "C", "D", "E", "F", "G")

    val rdd = sc.makeRDD(list, 2)
    // 转换数据，
    val wordRDD = rdd.map(s => (s, 1))
    // sample 算子需要传递三个参数
    // 1.第一个表示，抽取数据后是否将数据放回ture（放回），false（丢弃）
    // 2.第二个表示，每条数据被抽取的概率（抽出多少，这是一个double类型的参数,0-1之间，eg：0.3表示抽出30%）
    // 3.第三个表示，抽取数据是随机数算法的种子
    //             表示一个种子，根据这个seed随机抽取，一般情况下只用前两个参数就可以，那么这个参数是干嘛的呢，
    //             这个参数一般用于调试，有时候不知道是程序出问题还是数据出了问题，就可以将这个参数设置为定值
    // 如果不传递第三个参数，那么使用当前系统时间

    // 数据抽取
    val sampleWordRDD = wordRDD.sample(true, 0.4)

    // 统计抽取的数据的次数
    val wordsRDD = sampleWordRDD.groupBy(t => t._1)
    val wordCount = wordsRDD.map {
      case (word, iter) => {
        (word, iter.size)
      }
    }
    wordCount.collect().foreach(println)
    sc.stop()

    /**
     * 运行结果：
     * (B,3)
     * (G,1)
     * (A,30)
     * (C,1)
     * (E,1)
     *
     * 可以发现 key A 出现的次数远大于其他key，可以判断是由于 A 导致的数据倾斜
     */

  }
}
