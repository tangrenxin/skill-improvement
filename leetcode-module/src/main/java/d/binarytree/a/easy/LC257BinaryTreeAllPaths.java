package d.binarytree.a.easy;

import d.binarytree.NodeUtil;
import d.binarytree.TreeNode;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Description:
 * 【257. 二叉树的所有路径】
 *
 * 给你一个二叉树的根节点 root ，按 任意顺序 ，返回所有从根节点到叶子节点的路径。
 *
 * 叶子节点 是指没有子节点的节点。
 *
 * @Author: tangrenxin
 * @Date: 2021/11/21 09:57
 */
public class LC257BinaryTreeAllPaths {

  /**
   * 无论使用什么遍历方式，这道题的关键在于，应该如何保存节点的路径。
   * 总结下来有：
   * 深度优先搜索：用一个队列或者栈来存储节点的路径（双向队列Deque使用起来比较方便）
   * 广度优先搜索：可以用hashMap来保存当前节点跟父节点的关系，当遍历到叶子节点时，根据map回溯找到根节点，再做一个反转，
   *            就能得到当前叶子节点的路径
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
    System.out.println(binaryTreePaths(root));
    System.out.println(binaryTreePaths2(root));
  }


  /**
   * 方法一：深度优先搜索
   * @param root
   * @return
   */

  public static List<String> binaryTreePaths(TreeNode root) {
    // 结果 list
    List<String> ret = new ArrayList<>();
    // 用于存放从根节点到当前节点的路径
    Deque<TreeNode> path = new LinkedList<>();
    dfs(root, ret, path);

    return ret;
  }

  private static void dfs(TreeNode root, List<String> ret, Deque<TreeNode> path) {
    // 1.找到大问题是什么？--从根节点出发，输出所有到叶子节点的路径
    // 2.找到最简单的问题是什么？满足最简单问题时应该做什么？--节点为空，返回；
    if (root == null) {
      return;
    }
    path.offerLast(root);
    // 3.找到重复逻辑是什么？-- 判断当前节点是否是根节点，是的话保存路径，不是的话遍历子节点
    if (root.left == null && root.right == null) {
      // 如果当前节点是叶子节点，将路径保存到 ret中
      addPath(ret, path);
      // 添加完路径后 从path中移除当前节点
      path.pollLast();
    } else {
      // 4.自己调用自己
      dfs(root.left, ret, path);
      dfs(root.right, ret, path);
      // 孩子节点都遍历完后，移除当前节点
      path.pollLast();
    }
    // 5.输出结果
  }

  private static void addPath(List<String> ret, Deque<TreeNode> path) {
    StringBuffer buffer = new StringBuffer();
    int i = 1;
    for (TreeNode treeNode : path) {
      if (i++ == path.size()) {
        buffer.append(treeNode.val);
      } else {
        buffer.append(treeNode.val).append("->");
      }
    }
    ret.add(buffer.toString());
  }

  /**
   * 方法二：广度优先搜索
   * @param root
   * @return
   */

  public static List<String> binaryTreePaths2(TreeNode root) {
    // 结果 list
    List<String> ret = new ArrayList<>();
    // 用于存放从根节点到当前节点的路径
    HashMap<TreeNode, TreeNode> map = new HashMap<>();
    if (root == null) {
      return ret;
    }
    // 定义一个队列，用于存储将要访问的节点
    Queue<TreeNode> queue = new LinkedBlockingDeque<>();
    queue.offer(root);
    map.put(root, null);
    while (!queue.isEmpty()) {
      TreeNode node = queue.poll();
      // 如果当前节点是叶子节点，则保存路径到 ret
      if (node.left == null && node.right == null) {
        addPath(node, ret, map);
      } else {
        if (node.left != null) {
          queue.offer(node.left);
          map.put(node.left,node);
        }
        if (node.right != null) {
          queue.offer(node.right);
          map.put(node.right,node);
        }
      }
    }
    return ret;
  }

  private static void addPath(TreeNode node, List<String> ret, HashMap<TreeNode, TreeNode> map) {
    // 定义一个栈，用来反转链表
    Stack<TreeNode> stack = new Stack<>();
    while (node != null) {
      stack.push(node);
      node = map.get(node);
    }

    StringBuffer buffer = new StringBuffer();
    while (!stack.isEmpty()) {
      TreeNode treeNode = stack.pop();
      if (stack.size() == 0) {
        buffer.append(treeNode.val);
      } else {
        buffer.append(treeNode.val).append("->");
      }
    }
    ret.add(buffer.toString());
  }

}
