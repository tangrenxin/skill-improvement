package com.b.project.b.networkflowAnalysis.a.hotPagesNetWorkFlow

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/11/21 19:23
 */
// 定义输入数据样例类
case class ApacheLogEvent(ip: String, userId: String, timestamp: Long, method: String, url: String)

// 定义窗口聚合结果样例类
case class PageViewCount(url: String, windowEnd: Long, count: Long)
