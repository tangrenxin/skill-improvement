package d.binarytree.a.easy;

import d.binarytree.NodeUtil;
import d.binarytree.TreeNode;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Description:
 * 评论 (1.2k)
 * 题解 (1.8k)
 * 提交记录
 * 【112. 路径总和】
 *
 * 给你二叉树的根节点 root 和一个表示目标和的整数 targetSum ，判断该树中是否存在 根节点到叶子节点 的路径，
 * 这条路径上所有节点值相加等于目标和 targetSum 。
 *
 * 叶子节点 是指没有子节点的节点。
 *
 * @Author: tangrenxin
 * @Date: 2021/11/20 09:48
 */
public class LC112HasPathSum {

  /**
   *
   * 注意到本题的要求是，询问是否有从【根节点】到某个【叶子节点】经过的路径上的节点值和等于目标和。
   * 核心思想是对树的一次遍历，在遍历时记录从根节点到当前节点的路径和，以防止重复计算。
   *
   * 方法一：广度优先搜索
   * 思路及算法：
   * 使用广度优先搜索的方式，记录从根节点到当前节点的路径总和，以防止重复计算。
   * 这样我们使用两个队列，分别存储将要遍历的节点，以及根节点到这些节点的路径之和即可
   *
   * 方法二：递归
   * 思路及算法
   * 观察要求我们完成的函数，我们可以归纳出它的功能：询问是否存在从当前节点root到叶子节点的路径，
   * 满足其路径和为 sum.
   *
   * 假定从根节点到当前节点的值之和为 val，我们可以将这个大问题转化为一个小问题：是否存在从当前节点的子节点到叶子的路径
   * 满足其离京总和为 sum-val.
   *
   * 不难发现这满足 递归的性质，若当前节点就是叶子节点，那么我们直接判断sum 是否等于 val 即可（因为路径和已经确定，就是
   * 当前节点的值，我们只需要判断该路径和是否满足条件）。若当前界定啊不是叶子节点，我们只需要递归地询问他的子节点是否能满
   * 足条件即可。
   *
   */

  public static void main(String[] args) {
    String str = "[5,4,8,11,null,13,4,7,2,null,null,null,1]";
    /**
     *           5
     *          / \
     *         4   8
     *       /   / \
     *      11  13  4
     *     / \   \
     *    7   2   1
     */
//    String str = "[1,2,3]";
    NodeUtil nodeUtil = new NodeUtil();
    TreeNode root = nodeUtil.createTree(str);
    System.out.println(hasPathSum(root, 17));
    System.out.println(hasPathSum2(root, 17));
  }

  /**
   * 方法一：广度优先搜索遍历
   *
   * @param root
   * @param targetSum
   * @return
   */
  public static boolean hasPathSum(TreeNode root, int targetSum) {
    if (root == null) {
      return false;
    }
    // 新建队列 nodeQueue 用于存放将要遍历的节点
    Queue<TreeNode> nodeQueue = new LinkedBlockingDeque<>();
    // 新建队列 valNode 用于存放根节点到某节点的路径之和
    Queue<Integer> valQueue = new LinkedBlockingDeque<>();
    // 先将根节点入队
    nodeQueue.offer(root);
    // 根节点的val入队
    valQueue.offer(root.val);
    while (!nodeQueue.isEmpty()) {
      TreeNode currNode = nodeQueue.poll();
      Integer currVal = valQueue.poll();
      // 如果当前节点是叶子节点，判断到当前节点的和是否等于目标值
      if (currNode.left == null && currNode.right == null && currVal == targetSum) {
        return true;
      }

      if (currNode.left != null) {
        // 左孩子入队
        nodeQueue.offer(currNode.left);
        // 当前sum + 左孩子的val入队，对应着根节点到左孩子的路径之和
        valQueue.offer(currVal + currNode.left.val);
      }

      if (currNode.right != null) {
        nodeQueue.offer(currNode.right);
        valQueue.offer(currVal + currNode.right.val);
      }
    }
    return false;
  }

  /**
   * 方法二：递归
   * @param root
   * @param targetSum
   * @return
   */
  public static boolean hasPathSum2(TreeNode root, int targetSum) {
    if (root == null) {
      return false;
    }
    if (root.left == null && root.right == null) {
      return root.val == targetSum;
    }
    return hasPathSum2(root.left, targetSum - root.val) || hasPathSum2(root.right,
        targetSum - root.val);
  }

}
