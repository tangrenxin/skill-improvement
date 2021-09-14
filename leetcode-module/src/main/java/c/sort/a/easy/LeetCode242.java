package c.sort.a.easy;

import java.util.HashMap;

/**
 * @Description:
 * 242. 有效的字母异位词
 *
 * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
 *
 * 注意：若 s 和 t 中每个字符出现的次数都相同，则称 s 和 t 互为字母异位词。
 *
 *
 *
 * 示例 1:
 *
 * 输入: s = "anagram", t = "nagaram"
 * 输出: true
 *
 * 示例 2:
 *
 * 输入: s = "rat", t = "car"
 * 输出: false
 *
 *
 *
 * 提示:
 *
 *     1 <= s.length, t.length <= 5 * 104
 *     s 和 t 仅包含小写字母
 *
 *
 *
 * 进阶: 如果输入字符串包含 unicode 字符怎么办？你能否调整你的解法来应对这种情况？
 * @Author: tangrenxin
 * @Date: 2021/9/9 00:52
 */
public class LeetCode242 {

  public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) {
      return false;
    }
    HashMap<Character, Integer> table = new HashMap<Character, Integer>();
    for (int i = 0; i < s.length(); i++) {
      char ch = s.charAt(i);
      table.put(ch, table.getOrDefault(ch, 0) + 1);
    }
    for (int i = 0; i < t.length(); i++) {
      char ch = t.charAt(i);
      table.put(ch, table.getOrDefault(ch, 0) - 1);
      if (table.get(ch) < 0) {
        return false;
      }
    }
    return true;
  }

}
