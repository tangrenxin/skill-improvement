package core.a.distribution.test.stage3

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/3/4 02:44
 */
class SubTask extends Serializable {

  var datas: List[Int] = _

  var logic: (Int) => Int = _

  // 定义计算函数
  def compute(): List[Int] = {
    datas.map(logic)
  }
}
