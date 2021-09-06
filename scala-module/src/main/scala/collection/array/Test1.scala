package collection.array

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/9/3 11:34
 */
object Test1 {

  def main(args: Array[String]): Unit = {
    /**
     * 声明数组
     */
    var x:Array[String] = new Array[String](3)
    var y = new Array[String](3)
    println(x(0))
    println(y(0))
    x(0)="xiaoming"
    x(1)="shanshan"
    println(x(0))
    println(y(0))
    var z = Array("Runoob", "Baidu", "Google")


    println(z.head)
    println(z.tail)

  }
}
