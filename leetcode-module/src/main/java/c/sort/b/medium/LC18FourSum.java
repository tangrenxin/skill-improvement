package c.sort.b.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * 18. 四数之和
 *
 * 给你一个由 n 个整数组成的数组 nums ，和一个目标值 target 。请你找出并返回满足下述全部条件且不重复的四元组
 * [nums[a], nums[b], nums[c], nums[d]] ：
 *
 *     0 <= a, b, c, d < n
 *     a、b、c 和 d 互不相同
 *     nums[a] + nums[b] + nums[c] + nums[d] == target
 *
 * 你可以按 任意顺序 返回答案 。
 *
 * 示例 1：
 *
 * 输入：nums = [1,0,-1,0,-2,2], target = 0
 * 输出：[[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
 *
 * 示例 2：
 *
 * 输入：nums = [2,2,2,2,2], target = 8
 * 输出：[[2,2,2,2]]
 *
 *
 *
 * 提示：
 *
 *     1 <= nums.length <= 200
 *     -10^9 <= nums[i] <= 10^9
 *     -10^9 <= target <= 10^9
 * @Author: tangrenxin
 * @Date: 2021/10/8 16:21
 */
public class LC18FourSum {

  public static void main(String[] args) {

  }

  /**
   * a+b+c+d=target
   * 解法与三数之和类似
   * 排序+双指针
   * 1.数组排序
   * 2.确定a，将 fourSum -> threeSum
   * 3.确定b，将 threeSum -> twoSum
   * 4.返回计算结果
   * @param nums
   * @param target
   * @return
   */
  public static List<List<Integer>> fourSum(int[] nums, int target) {
    List<List<Integer>> quadruplets = new ArrayList<>();
    if (nums == null || nums.length < 4) {
      return quadruplets;
    }
    Arrays.sort(nums);
    int length = nums.length;
    for (int i = 0; i < length - 3; i++) {
      if (i > 0 && nums[i] == nums[i - 1]) {
        continue;
      }
      if (nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3] > target) {
        break;
      }
      if (nums[i] + nums[length - 3] + nums[length - 2] + nums[length - 1] < target) {
        continue;
      }
      for (int j = i + 1; j < length - 2; j++) {
        if (j > i + 1 && nums[j] == nums[j - 1]) {
          continue;
        }
        if (nums[i] + nums[j] + nums[j + 1] + nums[j + 2] > target) {
          break;
        }
        if (nums[i] + nums[j] + nums[length - 2] + nums[length - 1] < target) {
          continue;
        }
        int left = j + 1, right = length - 1;
        while (left < right) {
          int sum = nums[i] + nums[j] + nums[left] + nums[right];
          if (sum == target) {
            quadruplets.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
            while (left < right && nums[left] == nums[left + 1]) {
              left++;
            }
            left++;
            while (left < right && nums[right] == nums[right - 1]) {
              right--;
            }
            right--;
          } else if (sum < target) {
            left++;
          } else {
            right--;
          }
        }
      }
    }
    return quadruplets;
  }
}
