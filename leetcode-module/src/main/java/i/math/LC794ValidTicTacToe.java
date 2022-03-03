package i.math;

/**
 * @Description:
 * 794. 有效的井字游戏
 *
 * 用字符串数组作为井字游戏的游戏板 board。当且仅当在井字游戏过程中，玩家有可能将字符放置成游戏板所显示的状态时，才返回 true。
 *
 * 该游戏板是一个 3 x 3 数组，由字符 " "，"X" 和 "O" 组成。字符 " " 代表一个空位。
 *
 * 以下是井字游戏的规则：
 *
 *     玩家轮流将字符放入空位（" "）中。
 *     第一个玩家总是放字符 “X”，且第二个玩家总是放字符 “O”。
 *     “X” 和 “O” 只允许放置在空位中，不允许对已放有字符的位置进行填充。
 *     当有 3 个相同（且非空）的字符填充任何行、列或对角线时，游戏结束。
 *     当所有位置非空时，也算为游戏结束。
 *     如果游戏结束，玩家不允许再放置字符。
 *
 * 示例 1:
 * 输入: board = ["O  ", "   ", "   "]
 * 输出: false
 * 解释: 第一个玩家总是放置“X”。
 *
 * 示例 2:
 * 输入: board = ["XOX", " X ", "   "]
 * 输出: false
 * 解释: 玩家应该是轮流放置的。
 *
 * 示例 3:
 * 输入: board = ["XXX", "   ", "OOO"]
 * 输出: false
 *
 * 示例 4:
 * 输入: board = ["XOX", "O O", "XOX"]
 * 输出: true
 *
 * 说明:
 *
 *     游戏板 board 是长度为 3 的字符串数组，其中每个字符串 board[i] 的长度为 3。
 *      board[i][j] 是集合 {" ", "X", "O"} 中的一个字符。
 * @Author: tangrenxin
 * @Date: 2021/12/9 上午12:25
 */
public class LC794ValidTicTacToe {

  public static void main(String[] args) {

  }


  /**
   * 分类讨论
   *
   * 思路
   *
   * 题目要求判断当前游戏板是否生效，我们思考游戏板生效的规则：
   *
   *     1.玩家轮流将字符放入空位 " " 中。第一个玩家总是放字符 "X"，且第二个玩家总是放字符 "O"。
   *     因为第一个玩家总是先手，这就要求游戏板中字符 "X" 的数量一定是大于等于字符 "O" 的数量。
   *     2."X" 和 "O" 只允许放置在空位中，不允许对已放有字符的位置进行填充。
   *     3.当有 3 个相同（且非空）的字符填充任何行、列或对角线时，游戏结束。
   *     当所有位置非空时，也算为游戏结束。如果游戏结束，玩家不允许再放置字符，不可能能出现二者同时获胜的情况，
   *     因此游戏板上不可能同时出现 3 个 "X" 在一行和 3 个 "O" 在另一行。
   *     4.获胜的玩家一定是在自己放棋后赢得比赛，赢得比赛后，立马停止放置字符。
   *         - 如果第一个玩家获胜，由于第一个玩家是先手，则次数游戏板中 "X" 的数量比 "O" 的数量多 1。
   *         - 如果第二个玩家获胜，则 "X" 的数量与 "O" 的数量相同。
   *
   * 以上条件包含了游戏板生效的全部情况，可以通过反证法验证上面分类条件的正确性。在合法的游戏板，只能有 3 种结果合法，
   * 要么没有任何玩家赢，要么玩家一赢，要么玩家二赢。我们可以通过检查两种棋的数量关系即可验证是否有效，
   * 同时我们要检测是否存在两个玩家同时赢这种非法情况。
   *
   * 算法实现细节如下:
   *
   *     1.首先统计游戏板上 "X" 和 "O" 的数量并记录在 xCount 和 oCount 中，如果不满足 xCount≥oCount，则此时为非法，直接返回 false。
   *     2.然后我们检查是否有玩家是否获胜，我们检查在棋盘的 3 行，3 列和 2 条对角线上是否有该玩家的连续 3 枚棋子。
   *     我们首先检测玩家一是否获胜，如果玩家一获胜,则检查 xCount 是否等于 oCount+1；
   *     我们继续检测玩家二是否获胜，如果玩家二获胜，则检查 xCount 是否等于 oCount。
   *     3.对于特殊情况如果两个玩家都获胜，是否可以检测出该非法情况？如果同时满足两个玩家都获胜，
   *     则 "X" 和 "O" 数量的合法的组合可能为 (3,3),(4,3),(4,4),(5,4)，对于 (3,3),(4,4)不满足玩家一获胜的检测条件，
   *     对于 (4,3),(5,4) 满足玩家一获胜的检测条件但不满足玩家二的获胜条件。
   * @param board
   * @return
   */

  public boolean validTicTacToe(String[] board) {
    // 先遍历得到 x o 对应的数量
    int xCount = 0, oCount = 0;
    for (String row : board) {
      for (char c : row.toCharArray()) {
        xCount = (c == 'X') ? (xCount + 1) : xCount;
        oCount = (c == 'O') ? (oCount + 1) : oCount;
      }
    }
    // 如果不满足 xCount ≥ oCount 非法，返回false
    if (oCount != xCount && oCount != xCount - 1) {
      return false;
    }
    // 判断玩家一是否赢，如果玩家一赢，xCount = oCount+1
    if (win(board, 'X') && oCount != xCount - 1) {
      return false;
    }
    // 判断玩家二是否赢，如果玩家二赢，xCount = oCount
    if (win(board, 'O') && oCount != xCount) {
      return false;
    }
    return true;
  }

  public boolean win(String[] board, char p) {
    for (int i = 0; i < 3; ++i) {
      // 纵向两个格子
      if (p == board[0].charAt(i) && p == board[1].charAt(i) && p == board[2].charAt(i)) {
        return true;
      }
      // 横向三个格子
      if (p == board[i].charAt(0) && p == board[i].charAt(1) && p == board[i].charAt(2)) {
        return true;
      }
    }
    // 左上角到右下角的对角线
    if (p == board[0].charAt(0) && p == board[1].charAt(1) && p == board[2].charAt(2)) {
      return true;
    }
    // 左下角到右上角的对角线
    if (p == board[0].charAt(2) && p == board[1].charAt(1) && p == board[2].charAt(0)) {
      return true;
    }
    return false;
  }
}
