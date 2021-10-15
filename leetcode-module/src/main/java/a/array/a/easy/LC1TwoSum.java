package a.array.a.easy;

import java.util.Arrays;

/**
 * @Description:
 * 1. 两数之和 (题目要求返回原数组的下标，我这里的代码返回的是 value)
 *
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
 *
 * 你可以按任意顺序返回答案。
 * 示例 1：
 *
 * 输入：nums = [2,7,11,15], target = 9
 * 输出：[0,1]
 * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
 *
 * 示例 2：
 *
 * 输入：nums = [3,2,4], target = 6
 * 输出：[1,2]
 *
 * 示例 3：
 *
 * 输入：nums = [3,3], target = 6
 * 输出：[0,1]
 *
 * 提示：
 *
 *     2 <= nums.length <= 104
 *     -109 <= nums[i] <= 109
 *     -109 <= target <= 109
 *     只会存在一个有效答案
 *
 * 进阶：你可以想出一个时间复杂度小于 O(n2) 的算法吗？
 * @Author: tangrenxin
 * @Date: 2021/10/8 15:26
 */
public class LC1TwoSum {

  public static void main(String[] args) {
//    int[] nums = {2, 7, 11, 15};
//    int[] nums = {3, 2, 4};
//    int target = 6;
    int[] nums = {1,2,4,8,16,32,64,128};
    int target = 82;
    int[] res = twoSum(nums, target);
    System.out.println(res[0] + "," + res[1]);
  }

  /**
   * 找出 a+b = target 的值
   * 排序+双指针
   * 1.先将数组排序
   * 2.定义一个头指针index1和尾指针index2
   * 3.排序后循环a,为保证数据不重复，如果nums[index1] == nums[index1 - 1]时直接跳过本次循环
   * 4.排序后循环b,从右到左判断 nums[index1] + nums[index2] 与 target的大小情况
   *    1）如果 nums[index1] + nums[index2] > target，index2--，即寻找更小的b与a相加
   *    2）上面的循环，如果 index1 == index2，随着 index1 后续的增加就不会满足 a+b = target的 b 了，就可以退出了
   *    3）如果 nums[index1] + nums[index2] == target，既是我们需要寻找的数
   * @param nums
   * @param target
   * @return
   */
  public static int[] twoSum(int[] nums, int target) {
    // 先将数组按照从小到大排列
    Arrays.sort(nums);
    int[] res = new int[2];

    // 定义尾指针
    int index2 = nums.length - 1;
    for (int index1 = 0; index1 < nums.length; index1++) {
      if (index1 > 0 && nums[index1] == nums[index1 - 1]) {
        continue;
      }
      // 保证index1 在index2的左侧
      while (index1 < index2 && nums[index1] + nums[index2] > target) {
        --index2;
      }
      // 随着 index1 后续的增加就不会满足 a+b = target的 b 了，就可以退出了
      if (index1 == index2) {
        break;
      }
      if (nums[index1] + nums[index2] == target) {
        res[0] = nums[index1];
        res[1] = nums[index2];
        break;
      }
    }
    return res;
  }
}
