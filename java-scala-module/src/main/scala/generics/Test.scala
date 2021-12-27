package generics

/**
 * @Description:
 * scala 泛型 测试
 *
 * 定义一个泛型方法
 * 在scala中使用方括号来定义类型参数
 * 语法格式def 方法名[泛型名称](..):返回值={ // demo}
 * @Author: tangrenxin
 * @Date: 2021/12/27 11:13
 */
object Test {

  // 不考虑泛型的实现
  def getMiddle(arr: Array[Int]) = arr(arr.length / 2)


  // 加入泛型支持
  def getMiddleElement[T](array: Array[T]) = {
    array(array.length / 2)
  }

}
