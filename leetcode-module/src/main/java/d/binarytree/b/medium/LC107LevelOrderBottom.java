package d.binarytree.b.medium;

import d.binarytree.NodeUtil;
import d.binarytree.TreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Description:
 * 【107. 二叉树的层序遍历 II】
 *
 * 给定一个二叉树，返回其节点值自底向上的层序遍历。 （即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）
 *
 * 例如：
 * 给定二叉树 [3,9,20,null,null,15,7],
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 * 返回其自底向上的层序遍历为：
 *
 * [
 *   [15,7],
 *   [9,20],
 *   [3]
 * ]
 *
 * @Author: tangrenxin
 * @Date: 2021/11/21 17:06
 */
public class LC107LevelOrderBottom {

  /**
   * 广度优先搜索，对树进行逐层遍历，用队列维护当前层的所有元素，当队列不为空的时候，
   * 求得当前队列的长度 size\textit{size}size，每次从队列中取出 size\textit{size}size 个元素进行拓展，然后进行下一次迭代。
   *
   * 方法一：先按照正常的层序遍历，得到lists，然后反转lists，就能得到结果
   *
   * 方法二: 每一层都从左到右输出，将每层的数据先入栈，最后再出栈，也是反转的思想
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
    List<List<Integer>> lists = levelOrderBottom(root);
    for (List<Integer> list : lists) {
      System.out.println(list);
    }
    System.out.println("================");
    List<List<Integer>> lists2 = levelOrderBottom2(root);
    for (List<Integer> list : lists2) {
      System.out.println(list);
    }

  }

  /**
   * 方法一: 每一层都从左到右输出 得到 lists，然后反转lists
   * @param root
   * @return
   */
  public static List<List<Integer>> levelOrderBottom(TreeNode root) {
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
    Collections.reverse(lists);
    return lists;
  }

  /**
   * 方法二: 每一层都从左到右输出，将每层的数据先入栈，最后再出栈，也是反转的思想
   * @param root
   * @return
   */
  public static List<List<Integer>> levelOrderBottom2(TreeNode root) {
    List<List<Integer>> lists = new ArrayList<>();
    Stack<List<Integer>> stack = new Stack<>();
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
      stack.push(list);
    }
    while (!stack.isEmpty()){
      lists.add(stack.pop());
    }
    return lists;
  }
}
