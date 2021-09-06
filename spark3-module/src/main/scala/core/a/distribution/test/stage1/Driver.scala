package core.a.distribution.test.stage1

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

    out.write(5)
    out.flush()
    out.close()
    client.close()
  }
}
