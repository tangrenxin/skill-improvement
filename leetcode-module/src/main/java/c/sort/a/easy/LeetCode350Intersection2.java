package c.sort.a.easy;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @Description:
 * 【350. 两个数组的交集 II】
 * 给定两个数组，编写一个函数来计算它们的交集。
 * 示例 1：
 *
 * 输入：nums1 = [1,2,2,1], nums2 = [2,2]
 * 输出：[2,2]
 *
 * 示例 2:
 *
 * 输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
 * 输出：[4,9]
 *
 *
 *
 * 说明：
 *
 * 输出结果中每个元素出现的次数，应与元素在两个数组中出现次数的最小值一致。
 * 我们可以不考虑输出结果的顺序。
 *
 * 进阶：
 *
 * 如果给定的数组已经排好序呢？你将如何优化你的算法？
 * 如果 nums1 的大小比 nums2 小很多，哪种方法更优？
 * 如果 nums2 的元素存储在磁盘上，内存是有限的，并且你不能一次加载所有的元素到内存中，你该怎么办？
 * @Author: tangrenxin
 * @Date: 2021/9/28 17:32
 */
public class LeetCode350Intersection2 {

  public static void main(String[] args) {
    int[] nums1 = {4, 9, 5}, nums2 = {9, 4, 9, 8, 4};
    int[] res = intersection(nums1, nums2);
    for (int re : res) {
      System.out.println(re);
    }
  }

  public static int[] intersection(int[] nums1, int[] nums2) {
    // <数字，次数>
    /**
     * 将nums1 存入map，<数字，次数> 且记录nums1中重复元素的个数
     * 遍历nums2
     * 若map存在 且次数大于0，写入结果mapRes,map中对应元素的次数减一，
     * 当map中元素已经<=0 时，nums2中还有与map中重复的数，只输出最小个数的数量，也就是nums1中的数量
     * 反之，直接遍历完
     */
    HashMap<Integer, Integer> map = new HashMap<>();
    for (int num : nums1) {
      if (map.containsKey(num)) {
        map.put(num, map.get(num) + 1);
      } else {
        map.put(num, 1);
      }
    }
    int count = 0;
    HashMap<Integer, Integer> mapRes = new HashMap<>();
    for (int num : nums2) {
      if (map.containsKey(num) && map.get(num) > 0) {
        count++;
        if (mapRes.containsKey(num)) {
          mapRes.put(num, mapRes.get(num) + 1);
        } else {
          mapRes.put(num, 1);
        }
        map.put(num, map.get(num) - 1);
      }
    }
    int[] res = new int[count];
    for (Integer re : mapRes.keySet()) {
      for (int i = 0; i < mapRes.get(re); i++) {
        res[--count] = re;
      }
    }
    return res;
  }

}
