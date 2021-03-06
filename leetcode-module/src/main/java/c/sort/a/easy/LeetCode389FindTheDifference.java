package c.sort.a.easy;

import java.util.Arrays;

/**
 * @Description:
 * 【389. 找不同】
 * 给定两个字符串 s 和 t，它们只包含小写字母。
 *
 * 字符串 t 由字符串 s 随机重排，然后在随机位置添加一个字母。
 *
 * 请找出在 t 中被添加的字母。
 * 示例 1：
 *
 * 输入：s = "abcd", t = "abcde"
 * 输出："e"
 * 解释：'e' 是那个被添加的字母。
 *
 * 示例 2：
 *
 * 输入：s = "", t = "y"
 * 输出："y"
 *
 * 示例 3：
 *
 * 输入：s = "a", t = "aa"
 * 输出："a"
 *
 * 示例 4：
 *
 * 输入：s = "ae", t = "aea"
 * 输出："a"
 *
 *
 *
 * 提示：
 *
 *     0 <= s.length <= 1000
 *     t.length == s.length + 1
 *     s 和 t 只包含小写字母
 * @Author: tangrenxin
 * @Date: 2021/9/29 14:53
 */
public class LeetCode389FindTheDifference {

  public static void main(String[] args) {
//    String s = "abcd", t = "abcde";
    String s = "", t = "r";
    System.out.println(findTheDifference(s,t));
    System.out.println(findTheDifference2(s,t));
  }

  // 排序
  public static char findTheDifference(String s, String t) {
    char[] sChars = s.toCharArray();
    char[] tChars = t.toCharArray();
    Arrays.sort(sChars);
    Arrays.sort(tChars);
    for (int i = 0; i < sChars.length; i++) {
      if (sChars[i] != tChars[i]) {
        return tChars[i];
      }
    }
    // 插入的元素是t的最后一个
    return tChars[tChars.length - 1];
  }

  // 求和：将字符串 sss 中每个字符的 ASCII 码的值求和，得到 AsA_sAs​；
  // 对字符串 ttt 同样的方法得到 AtA_tAt​。两者的差值 At−AsA_t-A_sAt​−As​ 即代表了被添加的字符。
  public static char findTheDifference2(String s, String t) {
    int as = 0, at = 0;
    for (int i = 0; i < s.length(); ++i) {
      as += s.charAt(i);
    }
    for (int i = 0; i < t.length(); ++i) {
      at += t.charAt(i);
    }
    return (char) (at - as);
  }



}
