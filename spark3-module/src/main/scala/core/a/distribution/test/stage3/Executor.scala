package core.a.distribution.test.stage3

import java.io.ObjectInputStream
import java.net.ServerSocket

/**
 * @Description: 模拟spark运行架构中的executor,
 * @Author: tangrenxin
 * @Date: 2021/3/4 02:33
 */
object Executor {

  def main(args: Array[String]): Unit = {
    // 启动服务器，接收数据
    val server = new ServerSocket(9999)

    println("服务器启动，等待接收数据")

    // 等待客户端的连接
    val client = server.accept()
    val in = client.getInputStream
    val objIn = new ObjectInputStream(in)
    // 读取数据
    val task = objIn.readObject().asInstanceOf[SubTask]
    val ints = task.compute()

    println("计算节点[9999]计算的结果："+ints)
    objIn.close()
    client.close()
    server.close()
  }
}
