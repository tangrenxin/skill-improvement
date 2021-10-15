package c.sort.a.easy;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @Description:
 * 506. 相对名次
 *
 * 给出 N 名运动员的成绩，找出他们的相对名次并授予前三名对应的奖牌。
 * 前三名运动员将会被分别授予 “金牌”，“银牌” 和“ 铜牌”（"Gold Medal", "Silver Medal", "Bronze Medal"）。
 *
 * (注：分数越高的选手，排名越靠前。)
 *
 * 示例 1:
 *
 * 输入: [5, 4, 3, 2, 1]
 * 输出: ["Gold Medal", "Silver Medal", "Bronze Medal", "4", "5"]
 * 解释: 前三名运动员的成绩为前三高的，因此将会分别被授予 “金牌”，“银牌”和“铜牌” ("Gold Medal", "Silver Medal" and "Bronze Medal").
 * 余下的两名运动员，我们只需要通过他们的成绩计算将其相对名次即可。
 *
 * 提示:
 *
 *     N 是一个正整数并且不会超过 10000。
 *     所有运动员的成绩都不相同。
 * @Author: tangrenxin
 * @Date: 2021/9/29 17:59
 */
public class LeetCode506FindRelativeRanks {

  public static void main(String[] args) {

    int[] s = {5, 4, 1, 2, 3};
    String[] res = findRelativeRanks(s);
    for (String re : res) {
      System.out.print(re+",");
    }

  }

  public static String[] findRelativeRanks(int[] score) {

    int[] ints = Arrays.copyOf(score, score.length);
    Arrays.sort(ints);
    HashMap<Integer, String> map = new HashMap<>();
    for (int i = 0; i < ints.length; i++) {
      if (i == ints.length - 1) {
        map.put(ints[i], "Gold Medal");
      } else if (i == ints.length - 2) {
        map.put(ints[i], "Silver Medal");
      } else if (i == ints.length - 3) {
        map.put(ints[i], "Bronze Medal");
      } else {
        map.put(ints[i], String.valueOf(ints.length - i));
      }
    }
    String[] res = new String[score.length];
    for (int i = 0; i < score.length; i++) {
      res[i] = map.get(score[i]);
    }
    return res;
  }
}
