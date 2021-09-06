package core.a.distribution.test.stage1

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
    // 读取数据
    val i = in.read()
    println("接收到客户端发送的数据："+i)
    in.close()
    client.close()
    server.close()
  }
}
