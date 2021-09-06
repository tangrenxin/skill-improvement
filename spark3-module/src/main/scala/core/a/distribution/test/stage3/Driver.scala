package core.a.distribution.test.stage3

import java.io.ObjectOutputStream
import java.net.Socket

/**
 * @Description: 模拟spark运行架构中的 Driver
 * @Author: tangrenxin
 * @Date: 2021/3/4 02:30
 */
object Driver {

  def main(args: Array[String]): Unit = {

    // 连接服务器
    val client1 = new Socket("localhost",9999)
    val client2 = new Socket("localhost",8888)
    val task = new Task()

    val out1 = client1.getOutputStream
    val outObject1 = new ObjectOutputStream(out1)
    val subTask1 = new SubTask
    subTask1.datas = task.datas.take(2)// 取两个size的数据
    subTask1.logic = task.logic
    outObject1.writeObject(subTask1)
    outObject1.flush()
    outObject1.close()
    client1.close()

    val out2 = client2.getOutputStream
    val outObject2 = new ObjectOutputStream(out2)
    val subTask2 = new SubTask
    subTask2.datas = task.datas.takeRight(2)// 取两个size的数据
    subTask2.logic = task.logic
    outObject2.writeObject(subTask2)
    outObject2.flush()
    outObject2.close()
    client2.close()

    println("客户端数据发送完毕")
  }
}
