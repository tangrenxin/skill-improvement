package core.a.distribution.test.stage3

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/3/4 02:44
 */
class Task extends  Serializable {

  val datas = List(1,2,3,4)

  val logic = (num:Int) =>{num * 2}

  // 定义计算函数
  def compute(): List[Int] ={
    datas.map(logic)
  }
}
