package c.sort.a.easy;

import java.util.HashMap;

/**
 * @Description:
 * 594. 最长和谐子序列
 *
 * 和谐数组是指一个数组里元素的最大值和最小值之间的差别 正好是 1 。
 *
 * 现在，给你一个整数数组 nums ，请你在所有可能的子序列中找到最长的和谐子序列的长度。
 *
 * 数组的子序列是一个由数组派生出来的序列，它可以通过删除一些元素或不删除元素、且不改变其余元素的顺序而得到。
 *
 * 示例 1：
 *
 * 输入：nums = [1,3,2,2,5,2,3,7]
 * 输出：5
 * 解释：最长的和谐子序列是 [3,2,2,2,3]
 *
 * 示例 2：
 *
 * 输入：nums = [1,2,3,4]
 * 输出：2
 *
 * 示例 3：
 *
 * 输入：nums = [1,1,1,1]
 * 输出：0
 *
 * 提示：
 *
 *     1 <= nums.length <= 2 * 10^4
 *     -10^9 <= nums[i] <= 10^9
 * @Author: tangrenxin
 * @Date: 2021/9/30 15:23
 */
public class LeetCode594FindLHS {

  public static void main(String[] args) {
    int[] nums0 = {1, 3, 2, 2, 5, 2, 3, 7};
    int[] nums1 = {1, 1, 1, 1};
    int[] nums2 = {1, 2, 3, 4};
    System.out.println(findLHS(nums0));
    System.out.println(findLHS(nums1));
    System.out.println(findLHS(nums2));
  }

  public static int findLHS(int[] nums) {

    // 1.遍历数组，并记录每个元素出现的次数
    HashMap<Integer, Integer> map = new HashMap<>();
    for (int num : nums) {
      if (map.containsKey(num)) {
        map.put(num, map.get(num) + 1);
      } else {
        map.put(num, 1);
      }
    }
    // 2.遍历map，加入当前元素为a
    // 1) a的次数与a-1的次数相加(为空设为0) 的结果与max进行比较，更新max
    // 2) a的次数与a+1的次数相加(为空设为0) 的结果与max进行比较，更新max
    int max = Integer.MIN_VALUE;
    int tmpCnt;
    for (Integer integer : map.keySet()) {
      if (map.containsKey(integer - 1) || map.containsKey(integer + 1)) {
        tmpCnt = map.containsKey(integer - 1) ? map.get(integer - 1) : 0;
        if ((map.get(integer) + tmpCnt) > max) {
          max = map.get(integer) + tmpCnt;
        }
        tmpCnt = map.containsKey(integer + 1) ? map.get(integer + 1) : 0;
        // 代码有冗余，可以抽成方法
        if ((map.get(integer) + tmpCnt) > max) {
          max = map.get(integer) + tmpCnt;
        }
      }
    }
    return max == Integer.MIN_VALUE ? 0 : max;
  }
}
