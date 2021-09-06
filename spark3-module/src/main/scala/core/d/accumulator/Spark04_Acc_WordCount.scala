package core.d.accumulator

import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

/**
 * @Description: 累加器 自定义实现
 * @Author: tangrenxin
 * @Date: 2021/3/11 01:23
 */
object Spark04_Acc_WordCount {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(List("hello", "spark", "hello"))

    // 创建累加器
    val wcAcc = new MyAccumulator()
    // 向Spark进行注册
    sc.register(wcAcc)
    rdd.foreach(
      word => {
        // 数据的累加 （使用累加器）
        wcAcc.add(word)
      }
    )

    // 获取累加器累加的结果
    println(wcAcc.value)
    sc.stop()
  }

  /**
   * 自定义数据累加器
   * 1.继承 AccumulatorV2，定义泛型
   * IN：累加器输入的数据类型
   * OUT：累加器返回的数据类型
   *
   * 2.重写方法（6个）
   *
   */
  class MyAccumulator extends AccumulatorV2[String, mutable.Map[String, Long]] {

    // 需要累加的值
    private var wcMap = mutable.Map[String, Long]()

    // 判断是否初始化
    override def isZero: Boolean = {
      wcMap.isEmpty
      }

    // 复制
    override def copy(): AccumulatorV2[String, mutable.Map[String, Long]] = {
      new MyAccumulator()
    }

    // 重置
    override def reset(): Unit = {
      wcMap.clear()
    }

    // 获取累加器需要计算的值
    override def add(word: String): Unit = {
      // word 是进来的 值
      // 计算累加值
      val newCnt = wcMap.getOrElse(word, 0L) + 1
      // 更新map
      wcMap.update(word, newCnt)
    }

    // Driver合并多个累加器
    override def merge(other: AccumulatorV2[String, mutable.Map[String, Long]]): Unit = {
      val map1 = this.wcMap
      val map2 = other.value
      map2.foreach {
        case (word, count) => {
          val newCnt = map1.getOrElse(word, 0L) + count
          map1.update(word, newCnt)
        }
      }
    }

    // 累加器结果
    override def value: mutable.Map[String, Long] = {
      wcMap
    }
  }

}
