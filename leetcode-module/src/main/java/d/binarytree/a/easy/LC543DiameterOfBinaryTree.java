package d.binarytree.a.easy;

import d.binarytree.NodeUtil;
import d.binarytree.TreeNode;

/**
 * @Description:
 * 543. 二叉树的直径
 *
 * 给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。
 * 这条路径可能穿过也可能不穿过根结点。
 *
 * 示例 :
 * 给定二叉树
 *
 *           1
 *          / \
 *         2   3
 *        / \
 *       4   5
 *
 * 返回 3, 它的长度是路径 [4,2,1,3] 或者 [5,2,1,3]。
 * 注意：两结点之间的路径长度是以它们之间边的数目表示。
 *
 * @Author: tangrenxin
 * @Date: 2021/12/8 上午10:57
 */
public class LC543DiameterOfBinaryTree {

  /**
   * 如果必须过根节点，两个结点路径长度中的最大值 = 左子树的最大深度+右子树的最大深度
   * 如果不必都过根节点，两个结点路径长度中的最大值 = （以各个节点为根节点的左子树的最大深度+右子树的最大深度）的最大值 + 1（根节点）
   * @param args
   */
  public static void main(String[] args) {
//    String str = "[1,2,3,4,5]";
    String str = "[3,1,2]";
//    String str = "[4,-7,-3,null,null,-9,-3,9,-7,-4,null,6,null,-6,-6,null,null,0,6,5,null,9,null,null,-1,-4,null,null,null,-2]";
    NodeUtil nodeUtil = new NodeUtil();
    TreeNode root = nodeUtil.createTree(str);
    System.out.println(diameterOfBinaryTree(root));
    System.out.println(dfsRoot(root));
  }

  /**
   * 深度优先
   * @param root
   * @return
   */
  public static int diameterOfBinaryTree(TreeNode root) {
    /**
     * 如果必须过根节点，两个结点路径长度中的最大值 = 左子树的最大深度+右子树的最大深度
     * 如果不必都过根节点，两个结点路径长度中的最大值 = （以各个节点为根节点的左子树的最大深度+右子树的最大深度）的最大值 + 1（根节点）
     *
     * 这个题的这个解法跟 【437. 路径总和 III】解法类似，都是任意两个节点的问题
     * 好好记下这种解题方式
     *
     * @param args
     */
    if (root == null || (root.left == null && root.right == null)) {
      return 0;
    }
    // 以当前节点为根节点时，其路径中的最大值=左子树的最大深度+右子树的最大深度
    int rootCnt = dfsRoot(root);
    // 调用自己，以左孩子为根节点
    int leftCnt = diameterOfBinaryTree(root.left);
    // 调用自己，以右孩子为根节点
    int rightCnt = diameterOfBinaryTree(root.right);
    // 返回最大的长度
    return Math.max(Math.max(rootCnt, leftCnt), rightCnt);
  }

  public static int dfsRoot(TreeNode root) {
    if (root == null) {
      return 0;
    }
    int leftMaxDepth = dfs(root.left);
    int rightMaxDepth = dfs(root.right);
    return leftMaxDepth + rightMaxDepth;
  }

  private static int dfs(TreeNode root) {
    if (root == null) {
      return 0;
    }
    return Math.max(dfs(root.left) + 1, dfs(root.right) + 1);
  }

}
