package com.b.project.a.hotitemsAnalysis

/**
 * @Description:
 * 定义样例类
 * @Author: tangrenxin
 * @Date: 2021/11/20 17:39
 */

// 定义输入数据样例类
case class UserBehavior(userId: Long, // 用户id
                        itemId: Long, // 商品id
                        categoryId: Int, // 商品所属类别
                        behavior: String, // 用户行为(pv/buy/cart/fav)
                        timestamp: Long) // 行为发生的时间戳，单位秒

// 定义窗口聚合结果样例类
case class ItemViewCount(itemId: Long, // 商品id
                         windowEnd: Long, // 时间窗口触发点
                         count: Long) // pv cunt
