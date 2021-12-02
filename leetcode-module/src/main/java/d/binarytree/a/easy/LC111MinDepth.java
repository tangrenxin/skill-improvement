package d.binarytree.a.easy;

import d.binarytree.NodeUtil;
import d.binarytree.TreeNode;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Description:
 * 【111. 二叉树的最小深度】
 *
 * 给定一个二叉树，找出其最小深度。
 *
 * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
 *
 * 说明：叶子节点是指没有子节点的节点。
 *
 * 示例 1：
 *
 * 输入：root = [3,9,20,null,null,15,7]
 * 输出：2
 *
 * 示例 2：
 *
 * 输入：root = [2,null,3,null,4,null,5,null,6]
 * 输出：5
 *
 * 提示：
 *
 *     树中节点数的范围在 [0, 105] 内
 *     -1000 <= Node.val <= 1000
 * @Author: tangrenxin
 * @Date: 2021/12/2 上午10:13
 */
public class LC111MinDepth {

  public static void main(String[] args) {
//    String str = "[5,4,8,11,null,13,4,7,2,null,null,null,1]";
//    String str = "[3,9,20,null,null,15,7]";
//    String str = "[2,null,3,null,4,null,5,null,6]";
    String str = "[2,null,3,null,null,null,4,null,5,null,6]";
    NodeUtil nodeUtil = new NodeUtil();
    TreeNode root = nodeUtil.createTree(str);
    System.out.println(minDepth(root));
    System.out.println(minDepth2(root));
  }

  /**
   * 方法一：深度优先搜索
   * 递归
   * @param root
   * @return
   */
  public static int minDepth(TreeNode root) {
    // 1.找到最大的问题是什么？求最小深度
    // 2.找到最简单的问题是什么？满足最简单问题时应该做什么？root==null return 0;
    if (root == null) {
      return 0;
    }
    if (root.left == null && root.right == null) {
      return 1;
    }
    // 3.找到重复的逻辑是什么？有孩子传递
    // 4.自己调自己
    // 注意，需要判断 孩子是否为空
    int minDepth = Integer.MAX_VALUE;
    if (root.left != null) {
      minDepth = Math.min(minDepth(root.left) + 1, minDepth);
    }
    if (root.right != null) {
      minDepth = Math.min(minDepth(root.right) + 1, minDepth);
    }
    // 5.染回结果
    return minDepth;
  }

  /**
   * 方法二：广度优先搜索
   * 定义两个变量，min，currLevel
   * 每遍历到叶子节点，比较二者取最小
   * @param root
   * @return
   */
  public static int minDepth2(TreeNode root) {
    if (root == null) {
      return 0;
    }
    Queue<TreeNode> queue = new LinkedBlockingDeque<>();
    queue.offer(root);
    int min = Integer.MAX_VALUE;
    int currLevel = 0;
    while (!queue.isEmpty()) {
      int len = queue.size();
      currLevel++;
      for (int i = 0; i < len; i++) {
        TreeNode node = queue.poll();
        if (node.left == null && node.right == null) {
          min = Math.min(min, currLevel);
        }
        if (node.left != null) {
          queue.offer(node.left);
        }
        if (node.right != null) {
          queue.offer(node.right);
        }
      }
    }
    return min;
  }
}
