package d.binarytree.b.medium;

import d.binarytree.NodeUtil;
import d.binarytree.TreeNode;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Description:
 * 【103. 二叉树的锯齿形层序遍历】
 *
 * 给定一个二叉树，返回其节点值的锯齿形层序遍历。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
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
 * 返回锯齿形层序遍历如下：
 *
 * [
 *   [3],
 *   [20,9],
 *   [15,7]
 * ]
 *
 * @Author: tangrenxin
 * @Date: 2021/11/21 17:06
 */
public class LC103ZigzagLevelOrder {

  /**
   * 此题是「102. 二叉树的层序遍历」的变种，最后输出的要求有所变化，要求我们按层数的奇偶来决定每一层的输出顺序。规定二
   * 叉树的根节点为第 000 层，如果当前层数是偶数，从左至右输出当前层的节点值，否则，从右至左输出当前层的节点值。
   *
   * 我们依然可以沿用第 102 题的思想，修改广度优先搜索，对树进行逐层遍历，用队列维护当前层的所有元素，当队列不为空的时候，
   * 求得当前队列的长度 size\textit{size}size，每次从队列中取出 size\textit{size}size 个元素进行拓展，然后进行下一次迭代。
   *
   * 为了满足题目要求的返回值为「先从左往右，再从右往左」交替输出的锯齿形，我们可以利用「双端队列」的数据结构来维护当前层
   * 节点值输出的顺序。
   *
   * 双端队列是一个可以在队列任意一端插入元素的队列。在广度优先搜索遍历当前层节点拓展下一层节点的时候我们仍然从左往右按
   * 顺序拓展，但是对当前层节点的存储我们维护一个变量 isOrderLeft\textit{isOrderLeft}isOrderLeft 记录是从左至右
   * 还是从右至左的：
   *     如果从左至右，我们每次将被遍历到的元素插入至双端队列的末尾。
   *     如果从右至左，我们每次将被遍历到的元素插入至双端队列的头部。
   * 当遍历结束的时候我们就得到了答案数组。

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
    List<List<Integer>> lists = zigzagLevelOrder(root);
    for (List<Integer> list : lists) {
      System.out.println(list);
    }
    System.out.println("================");
    List<List<Integer>> lists2 = zigzagLevelOrder2(root);
    for (List<Integer> list : lists2) {
      System.out.println(list);
    }
  }

  /**
   * 每一层都从左到右输出
   * @param root
   * @return
   */
  public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
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

  /**
   * 锯齿形层序遍历
   * @param root
   * @return
   */
  public static List<List<Integer>> zigzagLevelOrder2(TreeNode root) {
    List<List<Integer>> lists = new ArrayList<>();
    if(root == null){
      return lists;
    }
    Queue<TreeNode> queue = new LinkedBlockingDeque<>();
    queue.offer(root);
    // 第一层，从右往左输出
    boolean isOrderLeft = true;
    while (!queue.isEmpty()) {
      int len = queue.size();
      Deque<Integer> deque = new LinkedBlockingDeque<>();
      for (int i = 0; i < len; i++) {
        TreeNode node = queue.poll();
        if(isOrderLeft){
          deque.offerLast(node.val);
        } else {
          deque.offerFirst(node.val);
        }
        if(node.left!=null){
          queue.offer(node.left);
        }
        if(node.right!=null){
          queue.offer(node.right);
        }
      }
      lists.add(new LinkedList<>(deque));
      isOrderLeft = !isOrderLeft;
    }
    return lists;
  }

}
