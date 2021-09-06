package core.c.rdd.c.serial

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Description:
 * 使用到的类没有序列化序列化 异常演示
 * @Author: tangrenxin
 * @Date: 2021/3/10 02:33
 */
object Spark01_Exception_Show {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(List(1, 2, 3, 4))

    // Executor 端用到了没有序列化的 User
    val user = new User()

    /**
     * // 报异常
     * // org.apache.spark.SparkException: Task not serializable
     * // java.io.NotSerializableException: core.b.rdd.b.operator.b.action.Spark07_Action_Foreach$User
     * // 解决办法：
     * // 1.User 类混入序列化特质（继承序列化接口）
     * // 2.User 类改成样例类，因为在样例类编译时，会自动混入序列化特质
     *
     * 假设 rdd 是一个空的数据集：val rdd = sc.makeRDD(List[Int]())
     * foreach 时 没有执行user.age 还会报错吗？
     * 会，也是同样的错误 为啥呢？
     * RDD算子中传递的函数是会包含闭包操作，那么就会进行检测功能
     * 闭包检测：内部函数使用到了外部的变量时，会检测外部变量是否可序列化
     */
    rdd.foreach(
      num => {
        // Executor 端用到了没有序列化的 User
        println("age=" + (user.age + num))
      }
    )


    sc.stop()
  }

  // 1.User 类混入序列化特质（继承序列化接口）
  //  class User extends Serializable {
  // 2.User 类改成样例类，因为在样例类编译时，会自动混入序列化特质(实现可序列化接口)
  //  case class User() {
  class User {
    var age: Int = 30
  }

}
