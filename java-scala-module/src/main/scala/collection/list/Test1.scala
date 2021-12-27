package collection.list

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/9/3 11:22
 */
object Test1 {

  def main(args: Array[String]): Unit = {
    // 字符串列表
    val site: List[String] = List("Runoob", "Google", "Baidu")
    val site2: List[String] = List("Runoob2", "Google2", "Baidu2")
    val nums = Nil

    /**
     * Scala列表有三个基本操作：
     *
     * head 返回列表第一个元素
     * tail 返回一个列表，包含除了第一元素之外的其他元素
     * isEmpty 在列表为空时返回true
     */
    println( "第一网站是 : " + site.head )
    println( "最后一个网站是 : " + site.tail )
    println( "查看列表 site 是否为空 : " + site.isEmpty )
    println( "查看 nums 是否为空 : " + nums.isEmpty )
    println("----------------------------");

    /**
     * 连接列表
     * 你可以使用 ::: 运算符或 List.:::() 方法或 List.concat() 方法来连接两个或多个列表。实例如下:
     */
    // 使用 ::: 运算符
    var fruit = site ::: site2
    println( "site1 ::: site2 : " + fruit )

    // 使用 List.:::() 方法
    fruit = site.:::(site2)
    println( "site1.:::(site2) : " + fruit )

    // 使用 concat 方法
    fruit = List.concat(site, site2)
    println( "List.concat(site1, site2) : " + fruit  )
    println("----------------------------");

    /**
     * List.reverse
     * List.reverse 用于将列表的顺序反转，实例如下：
     */
    println( "site 反转前 : " + site )
    println( "site 反转后 : " + site.reverse )
    println("----------------------------");

    /**
     * 添加元素：
     * +: list 从列表头添加
     * list :+ 从列表尾添加
     */
    val sitenew = site :+ "Google"
    val sitenew2 = "Google" +: site
    println(site)
    println(sitenew)
    println(sitenew2)
    println("----------------------------");

    /**
     * 去重
     *
     */
    println(sitenew.distinct)
    println("----------------------------");

    /**
     * map
     */
    val listmap = sitenew.map(x => x+"rx")
    println(listmap)
    println("----------------------------");

    /**
     * reduce((x,y)=>x*y)
     */
    val numList = List(1,2,3,4,5,6,7,8,9)
    val i = numList.reduce((x,y)=>x+y)
    println("reduce:"+i)

  }
}
