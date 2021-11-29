package com.b.project.b.networkflowAnalysis.b.PageView

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/11/23 下午11:52
 */
object Test {

  def main(args: Array[String]): Unit = {
    val oaId: String = null

    val res = if (oaId == null) {
      BigInt(100, scala.util.Random).toString(36)+"-null"
    } else {
      oaId
    }

    println(res)
  }
}
