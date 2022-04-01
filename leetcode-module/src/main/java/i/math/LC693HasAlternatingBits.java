package i.math;

/**
 * @Description:
 * 693. 交替位二进制数
 *
 * 给定一个正整数，检查它的二进制表示是否总是 0、1 交替出现：换句话说，就是二进制表示中相邻两位的数字永不相同。
 *
 *
 *
 * 示例 1：
 *
 * 输入：n = 5
 * 输出：true
 * 解释：5 的二进制表示是：101
 *
 * 示例 2：
 *
 * 输入：n = 7
 * 输出：false
 * 解释：7 的二进制表示是：111.
 *
 * 示例 3：
 *
 * 输入：n = 11
 * 输出：false
 * 解释：11 的二进制表示是：1011.
 *
 * 提示：
 *
 *     1 <= n <= 231 - 1
 * @Author: tangrenxin
 * @Date: 2022/3/28 11:06
 */
public class LC693HasAlternatingBits {

  public static void main(String[] args) {
    System.out.println(hasAlternatingBits(7));
  }

  public static boolean hasAlternatingBits(int n) {
    String str = Integer.toBinaryString(n);
    System.out.println(str);
    char nextChar = '1';
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == nextChar) {
        nextChar = (nextChar == '1' ? '0' : '1');
        continue;
      } else {
        return false;
      }
    }
    return true;
  }
}
