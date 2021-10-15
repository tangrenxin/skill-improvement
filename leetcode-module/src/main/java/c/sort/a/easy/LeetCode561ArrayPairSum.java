package c.sort.a.easy;

import java.util.Arrays;

/**
 * @Description:
 *
 * 561. 数组拆分 I
 *
 * 给定长度为 2n 的整数数组 nums ，你的任务是将这些数分成 n 对,
 * 例如 (a1, b1), (a2, b2), ..., (an, bn) ，使得从 1 到 n 的 min(ai, bi) 总和最大。
 * 返回该 最大总和 。
 * 示例 1：
 *
 * 输入：nums = [1,4,3,2]
 * 输出：4
 * 解释：所有可能的分法（忽略元素顺序）为：
 * 1. (1, 4), (2, 3) -> min(1, 4) + min(2, 3) = 1 + 2 = 3
 * 2. (1, 3), (2, 4) -> min(1, 3) + min(2, 4) = 1 + 2 = 3
 * 3. (1, 2), (3, 4) -> min(1, 2) + min(3, 4) = 1 + 3 = 4
 * 所以最大总和为 4
 *
 * 示例 2：
 *
 * 输入：nums = [6,2,6,5,1,2]
 * 输出：9
 * 解释：最优的分法为 (2, 1), (2, 5), (6, 6). min(2, 1) + min(2, 5) + min(6, 6) = 1 + 2 + 6 = 9
 *
 *
 *
 * 提示：
 *
 *     1 <= n <= 104
 *     nums.length == 2 * n
 *     -104 <= nums[i] <= 104
 * @Author: tangrenxin
 * @Date: 2021/9/30 15:08
 */
public class LeetCode561ArrayPairSum {

  public static void main(String[] args) {
//    int[] nums = {6, 2, 6, 5, 1, 2};
    int[] nums = {1, 4, 3, 2};
    System.out.println(arrayPairSum(nums));
  }


  public static int arrayPairSum(int[] nums) {
    //  先排序，相邻两个数为一组求 min 再加和
    Arrays.sort(nums);
    int index = 0;
    int sum = 0;
    while (index < nums.length && (index + 1) < nums.length) {
      sum += Math.min(nums[index], nums[index + 1]);
      index += 2;
    }
    return sum;
    // 优化：因为已经从小到大排好序，每一轮的Math.min(nums[index], nums[index + 1])取的都是 nums[index]，
    // 也就是index为偶数的数据，可以改成：
//    for (int i = 0; i < nums.length; i+=2) {
//      sum += nums[i];
//    }
  }
}
