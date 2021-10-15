package c.sort.b.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @Description:
 * 15. 三数之和
 *
 * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，
 * 使得 a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组。
 *
 * 注意：答案中不可以包含重复的三元组。
 *
 * 示例 1：
 *
 * 输入：nums = [-1,0,1,2,-1,-4]
 * 输出：[[-1,-1,2],[-1,0,1]]
 *
 * 示例 2：
 *
 * 输入：nums = []
 * 输出：[]
 *
 * 示例 3：
 *
 * 输入：nums = [0]
 * 输出：[]
 *
 * 提示：
 *     0 <= nums.length <= 3000
 *     -105 <= nums[i] <= 105
 * @Author: tangrenxin
 * @Date: 2021/10/8 10:41
 */
public class LC15ThreeSum {

  public static void main(String[] args) {
//    int[] nums = {-1, 0, 1, 2, -1, -4};
    int[] nums = {-1, 0, 1, 0, 3};
    List<List<Integer>> lists = threeSum(nums);
    for (List<Integer> list : lists) {
      System.out.println(list.get(0) + "," + list.get(1) + "," + list.get(2));
    }
  }


  /**
   * 方法一：暴力解法
   * 三重循环
   * 时间复杂度O(N^3)
   * 这个方法会超时
   * @param nums
   * @return
   */
  public static List<List<Integer>> threeSum(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> res = new ArrayList<>();
    if (nums == null || nums.length < 3) {
      return res;
    }

    HashMap<Integer, Integer> map = new HashMap<>();
    for (int num : nums) {
      map.put(num, map.containsKey(num) ? map.get(num) + 1 : 1);
    }
    for (int i = 0; i < nums.length; i++) {
      if (i > 0 && nums[i] == nums[i - 1]) {
        continue;
      }
      for (int j = i + 1; j < nums.length; j++) {
        int tmp = nums[i] + nums[j];
        if (map.containsKey(-1 * tmp)) {
          int count = 0;
          if (nums[i] == (-1 * tmp)) {
            count++;
          }
          if (nums[j] == (-1 * tmp)) {
            count++;
          }
          if (map.get(-1 * tmp) <= count) {
            continue;
          }
          List<Integer> list = new ArrayList<>();
          list.add(nums[i]);
          list.add(nums[j]);
          list.add(-1 * tmp);
          Collections.sort(list);
          boolean isRepeat = checkIsRepeat(res, list);
          if (!isRepeat) {
            res.add(list);
          }
        }
      }
    }
    return res;
  }

  /**
   * 求 a+b+c=target
   * 方法二：排序 + 双指针
   * 1.先将数组排序
   * 2.a对应指针first，b对应指针second，c对应指针third
   * 3.第一层循环，指定a，a确定后，剩下的起始就是b+c=(-a)的twoSum问题
   * 4.第二层循环，second=first+1开始，寻找b+c=(-a)的值
   * 5.返回结果
   * 排序复杂度：O(nlogn)
   * 搜索复杂度：O(n^2)
   * @param nums
   * @return
   */
  public static List<List<Integer>> threeSum2(int[] nums) {
    // 1.先将数组排序
    Arrays.sort(nums);
    List<List<Integer>> res = new ArrayList<>();
    if (nums == null || nums.length < 3) {
      return res;
    }
    int n = nums.length;
    // 2.a对应指针first，b对应指针second，c对应指针third
    // 3.第一层循环，指定a，a确定后，剩下的起始就是b+c=(-a)的twoSum问题
    for (int first = 0; first < n; first++) {
      // 需要和上一次枚举的数不相同
      if (first > 0 && nums[first] == nums[first - 1]) {
        continue;
      }
      // c 对应的指针third初始指向数组的最右端
      int third = n - 1;
      int target = -nums[first];
      // 4.第二层循环，second=first+1开始，寻找b+c=(-a)的值
      for (int second = first + 1; second < n; ++second) {
        // 需要和上一次枚举的数不相同
        if (second > first + 1 && nums[second] == nums[second - 1]) {
          continue;
        }
        // 需要保证 b 的指针在 c 的指针的左侧
        while (second < third && nums[second] + nums[third] > target) {
          --third;
        }
        // TODO (这一步是减少循环次数的关键)
        // 如果指针重合，随着 b 后续的增加
        // 就不会有满足 a+b+c=0 并且 b<c 的 c 了，可以退出循环
        if (second == third) {
          break;
        }
        if (nums[second] + nums[third] == target) {
          List<Integer> list = new ArrayList<Integer>();
          list.add(nums[first]);
          list.add(nums[second]);
          list.add(nums[third]);
          res.add(list);
        }
        // 程序执行到这，说明nums[second] + nums[third] < target
        // 按理应该移动second指针，但结束本次循环既是移动second指针
      }
    }
    return res;
  }

  private static boolean checkIsRepeat(List<List<Integer>> res, List<Integer> list) {
    for (List<Integer> re : res) {
      if (re.get(0).equals(list.get(0))
          && re.get(1).equals(list.get(1))
          && re.get(2).equals(list.get(2))) {
        return true;
      }
    }
    return false;
  }
}
