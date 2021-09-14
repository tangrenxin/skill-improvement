package c.sort.a.easy;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import java.sql.SQLOutput;
import java.util.HashMap;

/**
 * @Description:
 * 题目描述
 * 评论 (1.3k)
 * 题解 (1.6k)
 * 提交记录
 * 217. 存在重复元素
 *
 * 给定一个整数数组，判断是否存在重复元素。
 *
 * 如果存在一值在数组中出现至少两次，函数返回 true 。如果数组中每个元素都不相同，则返回 false 。
 *
 *
 *
 * 示例 1:
 *
 * 输入: [1,2,3,1]
 * 输出: true
 *
 * 示例 2:
 *
 * 输入: [1,2,3,4]
 * 输出: false
 *
 * 示例 3:
 *
 * 输入: [1,1,1,3,3,4,3,2,4,2]
 * 输出: true
 * @Author: tangrenxin
 * @Date: 2021/9/8 23:47
 */
public class LeetCode217 {

  public static void main(String[] args) {
    System.out.println(1&0);
    System.out.println(1&1);
    System.out.println(1&2);
    System.out.println(1&3);
    System.out.println(1&4);
    System.out.println(1&5);
    System.out.println(1&6);
    System.out.println(1&7);
  }


  public boolean containsDuplicate(int[] nums) {

    HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
    for (int num : nums) {
      map.put(num,0);
    }
    return !(map.size() == nums.length);
  }
}
