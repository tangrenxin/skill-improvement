package c.sort.a.easy;

import java.util.Arrays;

/**
 * @Description:
 * 【268. 丢失的数字】
 * 给定一个包含 [0, n] 中 n 个数的数组 nums ，找出 [0, n] 这个范围内没有出现在数组中的那个数。
 *
 * 示例 1：
 *
 * 输入：nums = [3,0,1]
 * 输出：2
 * 解释：n = 3，因为有 3 个数字，所以所有的数字都在范围 [0,3] 内。2 是丢失的数字，因为它没有出现在 nums 中。
 *
 * 示例 2：
 *
 * 输入：nums = [0,1]
 * 输出：2
 * 解释：n = 2，因为有 2 个数字，所以所有的数字都在范围 [0,2] 内。2 是丢失的数字，因为它没有出现在 nums 中。
 *
 * 示例 3：
 *
 * 输入：nums = [9,6,4,2,3,5,7,0,1]
 * 输出：8
 * 解释：n = 9，因为有 9 个数字，所以所有的数字都在范围 [0,9] 内。8 是丢失的数字，因为它没有出现在 nums 中。
 *
 * 示例 4：
 *
 * 输入：nums = [0]
 * 输出：1
 * 解释：n = 1，因为有 1 个数字，所以所有的数字都在范围 [0,1] 内。1 是丢失的数字，因为它没有出现在 nums 中。
 *
 * 提示：
 *
 *     n == nums.length
 *     1 <= n <= 104
 *     0 <= nums[i] <= n
 *     nums 中的所有数字都 独一无二
 *
 * 进阶：你能否实现线性时间复杂度、仅使用额外常数空间的算法解决此问题?
 * @Author: tangrenxin
 * @Date: 2021/9/28 17:13
 */
public class LeetCode268MissingNumber {

  public static int missingNumber(int[] nums) {
    // 先对数组进行排序
    Arrays.sort(nums);

    // 判断 n 是否出现在末位
    if (nums[nums.length - 1] != nums.length) {
      return nums.length;
    }
    // 判断 0 是否出现在首位
    else if (nums[0] != 0) {
      return 0;
    }

    // 此时缺失的数字一定在 (0, n) 中
    for (int i = 1; i < nums.length; i++) {
      int expectedNum = nums[i - 1] + 1;
      if (nums[i] != expectedNum) {
        return expectedNum;
      }
    }

    // 未缺失任何数字（保证函数有返回值）
    return -1;
  }

}
