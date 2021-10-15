package c.sort.a.easy;

import java.util.Arrays;

/**
 * @Description:
 * 628. 三个数的最大乘积
 *
 * 给你一个整型数组 nums ，在数组中找出由三个数组成的最大乘积，并输出这个乘积。
 * 示例 1：
 *
 * 输入：nums = [1,2,3]
 * 输出：6
 *
 * 示例 2：
 *
 * 输入：nums = [1,2,3,4]
 * 输出：24
 *
 * 示例 3：
 *
 * 输入：nums = [-1,-2,-3]
 * 输出：-6
 *
 * 提示：
 *
 *     3 <= nums.length <= 10^4
 *     -1000 <= nums[i] <= 1000
 * @Author: tangrenxin
 * @Date: 2021/9/30 16:05
 */
public class LeetCode628MaximumProduct {

  public static void main(String[] args) {
//    int[] nums = {-1, -2, -3};
//    int[] nums = {1, 2, 3};
    int[] nums = {-100,-98,-1,2,3,4};
    System.out.println(maximumProduct(nums));
  }

  public static int maximumProduct(int[] nums) {
    // 先排序，数越大，乘积越大
    // 如果负数>=2个，需要考虑负数的情况（两个最小的负数与最大的正数的乘积 与 排序后三个最大值乘积比较）
    // 有负数参与的计算，什么时候乘积最大：两个最小的负数与最大的正数的乘积
    // 1.遍历数组，check 有多少个负数；
    int minusCnt = 0;
    for (int num : nums) {
      minusCnt += num < 0 ? 1 : 0;
    }
    Arrays.sort(nums);
    if (minusCnt > 1) {
    int minusRes = nums[0] * nums[1] * nums[nums.length - 1];
    int top3Res = nums[nums.length - 1] * nums[nums.length - 2] * nums[nums.length - 3];
    return Math.max(minusRes, top3Res);
    } else {
      return nums[nums.length - 1] * nums[nums.length - 2] * nums[nums.length - 3];
    }
  }

  // 优化版本
  public static int maximumProduct2(int[] nums) {
    // 先排序，数越大，乘积越大
    // 如果负数>=2个，需要考虑负数的情况（两个最小的负数与最大的正数的乘积 与 排序后三个最大值乘积比较）
    // 有负数参与的计算，什么时候乘积最大：两个最小的负数与最大的正数的乘积
    Arrays.sort(nums);
    int minusRes = nums[0] * nums[1] * nums[nums.length - 1];
    int top3Res = nums[nums.length - 1] * nums[nums.length - 2] * nums[nums.length - 3];
    return Math.max(minusRes, top3Res);
  }
}
