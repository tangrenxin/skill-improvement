package c.sort.a.easy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @Description:
 * 645. 错误的集合
 *
 * 集合 s 包含从 1 到 n 的整数。不幸的是，因为数据错误，导致集合里面某一个数字复制了成了集合里面的另外一个数字的值，
 * 导致集合 丢失了一个数字 并且 有一个数字重复 。
 *
 * 给定一个数组 nums 代表了集合 S 发生错误后的结果。
 *
 * 请你找出重复出现的整数，再找到丢失的整数，将它们以数组的形式返回。
 *
 * 示例 1：
 *
 * 输入：nums = [1,2,2,4]
 * 输出：[2,3]
 *
 * 示例 2：
 *
 * 输入：nums = [1,1]
 * 输出：[1,2]
 *
 * 提示：
 *
 *     2 <= nums.length <= 10^4
 *     1 <= nums[i] <= 10^4
 * @Author: tangrenxin
 * @Date: 2021/9/30 17:00
 */
public class LeetCode645FindErrorNums {

  public static void main(String[] args) {
    int[] nums = {3, 2, 3, 4, 6, 5};
    int[] res = findErrorNums(nums);
    System.out.println(res[0] + "," + res[1]);
  }

  public static int[] findErrorNums(int[] nums) {
    HashSet<Integer> set = new HashSet<>();
    int[] res = new int[2];
    for (int num : nums) {
      if(set.contains(num)){
        // 找到重复的数
        res[0] = num;
      }
      set.add(num);
    }
    for (int i = 1; i <= nums.length; i++) {
      if (!set.contains(i)) {
        // 找到丢失的数
        res[1] = i;
        break;
      }
    }
    return res;
  }
}
