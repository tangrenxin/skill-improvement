package d.binarytree.a.easy;

import d.binarytree.NodeUtil;
import d.binarytree.TreeNode;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Description:
 * 【404. 左叶子之和】
 *
 * 计算给定二叉树的所有左叶子之和。
 *
 * 示例：
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 * 在这个二叉树中，有两个左叶子，分别是 9 和 15，所以返回 24
 * @Author: tangrenxin
 * @Date: 2021/11/21 11:04
 */
public class LC404SumOfLeftLeaves {

  public static void main(String[] args) {
//    String str = "[5,4,8,11,null,13,4,7,2,null,null,null,1]";
    String str = "[1,2,3]";
    /**
     *           5
     *          / \
     *         4   8
     *       /   / \
     *      11  13  4
     *     / \   \
     *    7   2   1
     */
    NodeUtil nodeUtil = new NodeUtil();
    TreeNode root = nodeUtil.createTree(str);
    System.out.println(sumOfLeftLeaves(root));
    System.out.println(sumOfLeftLeaves2(root));
  }

  /**
   * 方法一：广度优先搜索
   * 队列1，存放将要访问的节点
   * 队列2，存放将要访问的节点对应的路径之和(本题要求 求左叶子节点之和，我自己想出的需求：求左叶子节点的路径和)
   * map：存放当前节点跟父节点的关系
   * @param root
   * @return
   */
  public static int sumOfLeftLeaves(TreeNode root) {
    int sum = 0;
    if (root == null || (root.left == null && root.right == null)) {
      return sum;
    }
    Queue<TreeNode> queue = new LinkedBlockingDeque<>();
    HashMap<TreeNode, TreeNode> map = new HashMap<>();
    queue.offer(root);
    map.put(root, null);
    while (!queue.isEmpty()) {
      TreeNode node = queue.poll();
      if (node.left == null && node.right == null) {
        TreeNode parentNode = map.get(node);
        // 如果当前节点是左节点（特别的，如果只有一个节点，也算左节点？题目没说，先这样处理）
        // -- 额，提交后发现，题目要求是 返回0 提到前面去判断
        // if (parentNode == null || parentNode.left == node) {
        if (parentNode.left == node) {
          sum += node.val;
        }
      } else {
        if (node.left != null) {
          queue.offer(node.left);
          map.put(node.left, node);
        }
        if (node.right != null) {
          queue.offer(node.right);
          map.put(node.right, node);
        }
      }
    }
    return sum;
  }

  /**
   * 方法二：深度优先搜索
   * 递归时，将sum和父节点下传
   * @param root
   * @return
   */
  public static int sumOfLeftLeaves2(TreeNode root) {
    return dfs(root, 0, null);
  }

  private static int dfs(TreeNode root, int sum, TreeNode parentNode) {
    // 1.找到大问题是什么？-- 求所有左孩子节点的和
    // 2.找到最简单的问题是什么？满足最简单问题时应该做什么？--root is null ,return 0
    if (root == null) {
      return sum;
    }
    // 3.找到重复逻辑是什么？-- 判断当前节点是不是左叶子节点，是的话，sum+val
    if (root.left == null && root.right == null) {
      if (parentNode != null && parentNode.left == root) {
        sum += root.val;
      }
      return sum;
    } else {
      // 4.自己调用自己
      // 5.返回结果
      return dfs(root.left, sum, root) + dfs(root.right, sum, root);
    }
  }

}
