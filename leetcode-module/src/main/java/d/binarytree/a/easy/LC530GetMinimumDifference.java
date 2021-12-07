package d.binarytree.a.easy;

import d.binarytree.NodeUtil;
import d.binarytree.TreeNode;

/**
 * @Description:
 * 【530. 二叉搜索树的最小绝对差】
 *
 * 给你一个二叉搜索树的根节点 root ，返回 树中任意两不同节点值之间的最小差值 。
 *
 * 差值是一个正数，其数值等于两值之差的绝对值。
 * 示例 1：
 *
 * 输入：root = [4,2,6,1,3]
 * 输出：1
 *
 * 示例 2：
 * 输入：root = [1,0,48,null,null,12,49]
 * 输出：1
 * 提示：
 *
 *     树中节点的数目范围是 [2, 104]
 *     0 <= Node.val <= 105
 *
 * @Author: tangrenxin
 * @Date: 2021/12/7 下午3:48
 */
public class LC530GetMinimumDifference {

  public static void main(String[] args) {
    String str1 = "[4,2,6,1,3]";
    NodeUtil nodeUtil = new NodeUtil();
    TreeNode root = nodeUtil.createTree(str1);
    System.out.println(new LC530GetMinimumDifference().getMinimumDifference(root));
  }

  int pre;
  int ans;

  /**
   * 要求二叉搜索树任意两节点差的绝对值的最小值，而我们知道二叉搜索树有个性质为二叉搜索树中序遍历得到的值序列是递增有序的，
   * 因此我们只要得到中序遍历后的值序列即能用上文提及的方法来解决。
   *
   * 朴素的方法是经过一次中序遍历将值保存在一个数组中再进行遍历求解，我们也可以在中序遍历的过程中用
   * pre 变量保存前驱节点的值，这样即能边遍历边更新答案，不再需要显式创建数组来保存，
   * 需要注意的是 pre 的初始值需要设置成任意负数标记开头，下文代码中设置为 −1。
   *
   * @param root
   * @return
   */
  public int getMinimumDifference(TreeNode root) {
    ans = Integer.MAX_VALUE;
    pre = -1;
    dfs(root);
    return ans;
  }

  public void dfs(TreeNode root) {
    if (root == null) {
      return;
    }
    dfs(root.left);
    if (pre == -1) {
      pre = root.val;
    } else {
      ans = Math.min(ans, root.val - pre);
      pre = root.val;
    }
    dfs(root.right);
  }
}
