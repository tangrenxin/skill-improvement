package d.binarytree.b.medium;

import d.binarytree.NodeUtil;
import d.binarytree.TreeNode;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Description:
 * 【102. 二叉树的层序遍历】
 *
 * 给你一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。
 *
 * 示例：
 * 二叉树：[3,9,20,null,null,15,7],
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 * 返回其层序遍历结果：
 *
 * [
 *   [3],
 *   [9,20],
 *   [15,7]
 * ]
 *
 * @Author: tangrenxin
 * @Date: 2021/11/21 17:06
 */
public class LC102LevelOrder {

  /**
   * 广度优先搜索，对树进行逐层遍历，用队列维护当前层的所有元素，当队列不为空的时候，
   * 求得当前队列的长度 size\textit{size}size，每次从队列中取出 size\textit{size}size 个元素进行拓展，然后进行下一次迭代。
   *
   * @param args
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
    NodeUtil nodeUtil = new NodeUtil();
    TreeNode root = nodeUtil.createTree(str);
    List<List<Integer>> lists = levelOrder(root);
    for (List<Integer> list : lists) {
      System.out.println(list);
    }

  }

  /**
   * 每一层都从左到右输出
   * @param root
   * @return
   */
  public static List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> lists = new ArrayList<>();
    if(root == null){
      return lists;
    }
    Queue<TreeNode> queue = new LinkedBlockingDeque<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
      int len = queue.size();
      List<Integer> list = new ArrayList<>();
      for (int i = 0; i < len; i++) {
        TreeNode node = queue.poll();
        list.add(node.val);
        if(node.left!=null){
          queue.offer(node.left);
        }
        if(node.right!=null){
          queue.offer(node.right);
        }
      }
      lists.add(list);
    }
    return lists;
  }
}
