package c.sort.a.easy;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @Description:
 * 【414. 第三大的数】
 * 给你一个非空数组，返回此数组中 第三大的数 。如果不存在，则返回数组中最大的数。
 *
 * 示例 1：
 *
 * 输入：[3, 2, 1]
 * 输出：1
 * 解释：第三大的数是 1 。
 *
 * 示例 2：
 *
 * 输入：[1, 2]
 * 输出：2
 * 解释：第三大的数不存在, 所以返回最大的数 2 。
 *
 * 示例 3：
 *
 * 输入：[2, 2, 3, 1]
 * 输出：1
 * 解释：注意，要求返回第三大的数，是指在所有不同数字中排第三大的数。
 * 此例中存在两个值为 2 的数，它们都排第二。在所有不同数字中排第三大的数为 1 。
 *
 * 提示：
 *
 *     1 <= nums.length <= 104
 *     -231 <= nums[i] <= 231 - 1

 * 进阶：你能设计一个时间复杂度 O(n) 的解决方案吗？
 * @Author: tangrenxin
 * @Date: 2021/9/29 15:08
 */
public class LeetCode414ThirdMax {

  public static void main(String[] args) {
//    int[] nums = {5, 2, 2};
    int[] nums = {1, 2, -2147483648};
    System.out.println(Integer.MIN_VALUE);
    System.out.println("======");
    System.out.println(thirdMax(nums));
  }

  // 排序法
  public static int thirdMax(int[] nums) {
    HashSet<Integer> set = new HashSet<>();
    for (int num : nums) {
      set.add(num);
    }
    int[] res = new int[set.size()];
    int index = 0;
    for (Integer integer : set) {
      res[index++] = integer;
    }
    Arrays.sort(res);
    if (res.length >= 3) {
      return res[res.length - 3];
    } else {
      return res[res.length - 1];
    }
  }

}
