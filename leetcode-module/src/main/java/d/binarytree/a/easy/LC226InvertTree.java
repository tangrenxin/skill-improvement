package d.binarytree.a.easy;

import d.binarytree.NodeUtil;
import d.binarytree.TreeNode;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Description:
 * 【226. 翻转二叉树】
 *
 * 翻转一棵二叉树。
 *
 * 示例：
 *
 * 输入：
 *
 *      4
 *    /   \
 *   2     7
 *  / \   / \
 * 1   3 6   9
 *
 * 输出：
 *
 *      4
 *    /   \
 *   7     2
 *  / \   / \
 * 9   6 3   1
 *
 * 备注:
 * 这个问题是受到 Max Howell 的 原问题 启发的 ：
 *
 *     谷歌：我们90％的工程师使用您编写的软件(Homebrew)，但是您却无法在面试时在白板上写出翻转二叉树这道题，这太糟糕了。
 * @Author: tangrenxin
 * @Date: 2021/12/6 上午11:23
 */
public class LC226InvertTree {

  public static void main(String[] args) {
    String str = "";
    NodeUtil nodeUtil = new NodeUtil();
    TreeNode root = nodeUtil.createTree(str);
  }

  /**
   * 方法一：深度优先搜索
   * @param root
   * @return
   */
  public static TreeNode invertTree(TreeNode root) {
    if (root == null) {
      return root;
    }
    TreeNode left = root.left;
    TreeNode right = root.right;
    root.left = right;
    root.right = left;
    invertTree(root.left);
    invertTree(root.right);
    return root;
  }

  /**
   * 方法二：广度优先搜索
   * @param root
   * @return
   */
  public static TreeNode invertTree2(TreeNode root) {
    if (root == null) {
      return root;
    }
    Queue<TreeNode> queue = new LinkedBlockingDeque<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
      TreeNode node = queue.poll();
      TreeNode left = node.left;
      TreeNode right = node.right;
      node.left = right;
      node.right = left;
      if (node.left != null) {
        queue.offer(node.left);
      }
      if (node.right != null) {
        queue.offer(node.right);
      }
    }
    return root;
  }
}
