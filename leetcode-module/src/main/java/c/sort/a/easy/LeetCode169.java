package c.sort.a.easy;

import java.util.Arrays;

/**
 * @Description:
 * 题目描述
 * 评论 (1.4k)
 * 题解 (2.1k)
 * 提交记录
 * 169. 多数元素
 *
 * 给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
 *
 * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
 *
 *
 *
 * 示例 1：
 *
 * 输入：[3,2,3]
 * 输出：3
 *
 * 示例 2：
 *
 * 输入：[2,2,1,1,1,2,2]
 * 输出：2
 *
 *
 *
 * 进阶：
 *
 *     尝试设计时间复杂度为 O(n)、空间复杂度为 O(1) 的算法解决此问题。
 * @Author: tangrenxin
 * @Date: 2021/9/8 22:46
 */
public class LeetCode169 {

  public static int majorityElement(int[] nums) {
    Arrays.sort(nums);
    return nums[(int) Math.floor(nums.length/2)];
  }
}
