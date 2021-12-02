package d.binarytree.a.easy;

import d.binarytree.NodeUtil;
import d.binarytree.TreeNode;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Description:
 * 【104. 二叉树的最大深度】
 *
 * 给定一个二叉树，找出其最大深度。
 *
 * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
 *
 * 说明: 叶子节点是指没有子节点的节点。
 *
 * 示例：
 * 给定二叉树 [3,9,20,null,null,15,7]，
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 * 返回它的最大深度 3 。
 * @Author: tangrenxin
 * @Date: 2021/12/1 上午11:12
 */
public class LC104MaxDepth {

  public static void main(String[] args) {
//    String str = "[3,9,20,null,null,15,7]";
    String str = "[5,4,8,11,null,13,4,7,2,null,null,null,1]";
    NodeUtil nodeUtil = new NodeUtil();
    TreeNode root = nodeUtil.createTree(str);
    System.out.println(maxDepth(root));
    System.out.println(maxDepth2(root));
  }

  /**
   * 方法一：深度优先搜索
   *      递归
   * @param root
   * @return
   */
  public static int maxDepth(TreeNode root) {
    // 1.找到最大的问题是什么？求最大深度
    // 2.找到最简单的问题是什么？满足最简单问题时做什么？root=null return 0;
    if (root == null) {
      return 0;
    }
    // 3.循环的逻辑什么？如果有孩子，递归
    // 4.自己调用自己
    // 5.返回结果
    return Math.max(maxDepth(root.left) + 1, maxDepth(root.right) + 1);
  }

  /**
   * 方法二：广度优先搜索
   * @param root
   * @return
   */
  public static int maxDepth2(TreeNode root) {
    if (root == null) {
      return 0;
    }
    // 定义一个队列用于存放将要遍历的节点
    Queue<TreeNode> queue = new LinkedBlockingDeque<>();
    queue.offer(root);
    int cnt = 0;
    while (!queue.isEmpty()) {
      cnt++;
      int len = queue.size();
      for (int i = 0; i < len; i++) {
        TreeNode node = queue.poll();
        if (node.left != null) {
          queue.offer(node.left);
        }
        if (node.right != null) {
          queue.offer(node.right);
        }
      }
    }
    return cnt;
  }

}
