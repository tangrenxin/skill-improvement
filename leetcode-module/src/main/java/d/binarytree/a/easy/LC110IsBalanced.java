package d.binarytree.a.easy;

import d.binarytree.NodeUtil;
import d.binarytree.TreeNode;

/**
 * @Description:
 * 【110. 平衡二叉树】
 *
 * 给定一个二叉树，判断它是否是高度平衡的二叉树。
 *
 * 本题中，一棵高度平衡二叉树定义为：
 *
 *     一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1 。
 *
 * 示例 1：
 *
 * 输入：root = [3,9,20,null,null,15,7]
 * 输出：true
 *
 * 示例 2：
 *
 * 输入：root = [1,2,2,3,3,null,null,4,4]
 * 输出：false
 *
 * 示例 3：
 *
 * 输入：root = []
 * 输出：true
 *
 * 提示：
 *
 *     树中的节点数在范围 [0, 5000] 内
 *     -104 <= Node.val <= 104
 *
 * @Author: tangrenxin
 * @Date: 2021/12/3 上午11:00
 */
public class LC110IsBalanced {

  /**
   * 一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1 。
   * 这道题中的平衡二叉树的定义是：二叉树的每个节点的左右子树的高度差的绝对值不超过 111，则二叉树是平衡二叉树。
   * 根据定义，一棵二叉树是平衡二叉树，当且仅当其所有子树也都是平衡二叉树，因此可以使用递归的方式判断二叉树是不是平衡二叉树，
   * 递归的顺序可以是自顶向下或者自底向上。
   * @param args
   */

  public static void main(String[] args) {
    String str1 = "[3,9,20,null,null,15,7]";
    NodeUtil nodeUtil = new NodeUtil();
    TreeNode root = nodeUtil.createTree(str1);
    System.out.println(isBalanced(root));
  }

  /**
   * 方法一：自上而下
   * 对于当前遍历到的节点，首先计算左右子树的高度，如果左右子对于当前遍历到的节点，首先计算左右子树的高度，
   * 如果左右子树的高度差是否不超过 1，再分别递归地遍历左右子节点，并判断左子树和右子树是否平衡。树的高度差是否不超过 1，
   * 再分别递归地遍历左右子节点，并判断左子树和右子树是否平衡。
   * @param root
   * @return
   */
  public static boolean isBalanced(TreeNode root) {
    if (root == null) {
      return true;
    }
    // 先判断左右子树的高度差是否大于 1，在判断左右子树是否平衡
    return Math.abs(dfsDepth(root.left) - dfsDepth(root.right)) <= 1 && isBalanced(root.left)
        && isBalanced(root.right);
  }

  private static int dfsDepth(TreeNode root) {
    if (root == null) {
      return 0;
    }
    return Math.max(dfsDepth(root.left), dfsDepth(root.right)) + 1;
  }
}
