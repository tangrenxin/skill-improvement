package core.a.distribution.test.stage2

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
    val client = new Socket("localhost",9999)

    val out = client.getOutputStream

    // 要输出对象，创建衣蛾对象输出流
    val outObject = new ObjectOutputStream(out)
    val task = new Task()
    outObject.writeObject(task)
    outObject.flush()
    outObject.close()
    client.close()

    println("客户端数据发送完毕")
  }
}
