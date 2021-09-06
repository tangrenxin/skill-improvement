package core.c.rdd.b.operator.a.transform.a.single.value

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description: flatMap
 * @Author: tangrenxin
 * @Date: 2021/3/7 23:38
 */
object Spark04_FlatMap {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    // 算子 flatMap
    // 将 List(List(1, 2),List(3,4)) 进行扁平化操作
    val rdd = sc.makeRDD(List(List(1, 2), List(3, 4)))

    rdd.collect().foreach(println)
    val flatMapRDD = rdd.flatMap(
      // 传递的是 一个List(1, 2)
      list => {
        list // 返回的是一个可迭代的集合 有点不太明白，看下一个例子
        // 第二遍复习我看懂了：
        /**
         * 传递的list是List(1, 2) 我们需要将List(1, 2)进行打平，然后返回结果，因为flatmap往往是入一出多
         * 对应这个例子，如list，返回多个元素，如何返回多个元素呢？放到list里~
         * 举个java的例子
         * 入 list
         * 出 resList
         *
         * for (i <- list){
         * resList.add(i)
         * }
         *
         * return resList;
         *
         * 可以发现，其实 resList = list，
         * 所以 上面的
         * list => {
         * list}
         * 就是这个意思
         *
         */
      }
    )
    flatMapRDD.collect().foreach(println)

    sc.stop()

  }
}
