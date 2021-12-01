package b.string.a.easy;

/**
 * @Description:
 * 【1446. 连续字符】
 *
 * 给你一个字符串 s ，字符串的「能量」定义为：只包含一种字符的最长非空子字符串的长度。
 *
 * 请你返回字符串的能量。
 *
 * 示例 1：
 *
 * 输入：s = "leetcode"
 * 输出：2
 * 解释：子字符串 "ee" 长度为 2 ，只包含字符 'e' 。
 *
 * 示例 2：
 *
 * 输入：s = "abbcccddddeeeeedcba"
 * 输出：5
 * 解释：子字符串 "eeeee" 长度为 5 ，只包含字符 'e' 。
 *
 * 示例 3：
 *
 * 输入：s = "triplepillooooow"
 * 输出：5
 *
 * 示例 4：
 *
 * 输入：s = "hooraaaaaaaaaaay"
 * 输出：11
 *
 * 示例 5：
 *
 * 输入：s = "tourist"
 * 输出：1
 *
 * 提示：
 *
 *     1 <= s.length <= 500
 *     s 只包含小写英文字母。
 * @Author: tangrenxin
 * @Date: 2021/12/1 上午10:07
 */
public class LC1446MaxPower {

  public static void main(String[] args) {
//    String s = "abbcccddddeeeeedcba";
//    String s = "hooraaaaaaaaaaay";
    String s = "h";
    maxPower(s);
  }

  /**
   * 状态编程的思路，从第一个字符开始遍历，如果字符相同，cnt++，否则判断当前字符长度与目前最长的字符长度maxCnt的大小，
   * 如果比当前最大字符长度长，更新最长字符及长度
   * @param s
   * @return
   */
  public static int maxPower(String s) {
    char maxChar ='0';
    int maxCnt = 0;
    char tmpChar='_';
    int tmpCnt=0;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if(c == tmpChar){
        tmpCnt++;
      } else {
        if(tmpCnt > maxCnt){
          maxChar = tmpChar;
          maxCnt = tmpCnt;
        }
        tmpChar = c;
        tmpCnt = 1;
      }
    }
    // 当字符串只有一个字符时的特殊处理
    if(tmpCnt > maxCnt){
      maxChar = tmpChar;
      maxCnt = tmpCnt;
    }
    System.out.println("最大子串字符："+maxChar+"\t 长度："+maxCnt);
    return maxCnt;
  }
}
