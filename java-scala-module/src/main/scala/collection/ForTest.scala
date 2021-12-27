package collection

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/9/3 10:46
 */
object ForTest {

  def main(args: Array[String]): Unit = {

   val list = List("a","b","c","d")

    // 遍历列表
    for(s <- list){
      println(s)
    }
    println("----------------------------");

    // i to j 语法(包含 j)的实例
    for( a <- 1 to 10){
      println( "Value of a: " + a );
    }
    println("----------------------------");

    //  i until j 语法(不包含 j)的实例:
    for (a <- 1 until 10 ){
      println(a)
    }
    println("----------------------------");

    /**
     * for 循环过滤
     * cala 可以使用一个或多个 if 语句来过滤一些元素。
     */
    val numList = List(1,2,3,4,5,6,7,8,9,10);
    for( a <- numList
         if a != 3; if a < 8 ){
      println( "Value of a: " + a );
    }
    println("----------------------------");

    /**
     * for 使用 yield
     * 你可以将 for 循环的返回值作为一个变量存储。
     */
    var retVal = for{ a <- numList
                      if a != 3; if a < 8
                      }yield a
    // 输出返回值
    for( a <- retVal){
      println( "Value of a: " + a );
    }
    println("----------------------------");

  }

}
