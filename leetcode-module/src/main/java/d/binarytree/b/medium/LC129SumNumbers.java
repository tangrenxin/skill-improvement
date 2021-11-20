package d.binarytree.b.medium;

import d.binarytree.NodeUtil;
import d.binarytree.TreeNode;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import javafx.scene.control.TableRow;

/**
 * @Description:
 * 【129. 求根节点到叶节点数字之和】(面试遇到过)
 * 给你一个二叉树的根节点 root ，树中每个节点都存放有一个 0 到 9 之间的数字。
 *
 * 每条从根节点到叶节点的路径都代表一个数字：
 *
 *     例如，从根节点到叶节点的路径 1 -> 2 -> 3 表示数字 123 。
 *
 * 计算从根节点到叶节点生成的 所有数字之和 。
 *
 * 叶节点 是指没有子节点的节点。
 *
 * @Author: tangrenxin
 * @Date: 2021/11/20 15:57
 */
public class LC129SumNumbers {

  /**
   * 这道题中，二叉树的每条从根节点到叶子节点的路径都代表一个数字。其实每个节点都对应一个数字，
   * 等于其父节点对应的数字乘以10再加上该节点的值（这里假设根节点的父节点对应的数字是0）。
   * 只要计算出每个叶子节点对应的数字，然后计算所有叶子节点对应的数字之和，即可得到结果。
   * 可以通过深度优先搜索和广度优先搜索实现。
   *
   * 方法一：深度优先搜索
   * 从根节点开始，遍历每个节点，【如果遇到叶子节点，则将叶子节点对应的数字加到数字之和】，
   * 如果当前节点不是叶子节点，则计算其子节点对应的数字，然后对子节点递归遍历。
   *
   * 方法二：广度优先搜索
   * 使用广度优先搜素，需要维护两个队列，分别存储节点和节点对应的数字。
   * 初始时，将根节点和根节点的值分别加入两个队列。每次从两个队列分别取出一个节点和一个数字，进行如下操作：
   * 1）如果当前节点是叶子节点，则将该节点对应的数字加到数字之和；
   * 2）如果当前节点不是叶子节点，则获得当前节点的非空子节点，并根据当前节点对应的数字和子节点的值计算子节点对应的数字，
   * 然后将子节点和子节点对应的数字分别加入两个队列。
   *
   */

  public static void main(String[] args) {
    String str = "[1,2,3]";
    NodeUtil nodeUtil = new NodeUtil();
    TreeNode root = nodeUtil.createTree(str);

    System.out.println(sumNumbers(root));
    System.out.println(sumNumbers2(root));
  }

  /**
   * 方法一：深度优先搜索
   * @param root
   * @return
   */
  public static int sumNumbers(TreeNode root) {
    int res = dfs(root, 0);
    return res;
  }

  public static int dfs(TreeNode root, int sum) {
    // 1.找到大问题是什么？
    // - 从跟节点到子节点的路径组成一个数，求所有子节点对应的数的和
    // 2.找到最简单的问题是什么？满足最简单问题时应该做什么？
    // - 最简单：root==null,应该做什么：return 0；
    // - 如果当前节点是叶子节点，sum+当前节点的val后返回
    if (root == null) {
      return 0;
    }
    sum = sum * 10 + root.val;
    // - 如果当前节点是叶子节点，sum+当前节点的val后返回
    if (root.left == null && root.right == null) {
      return sum;
    } else {
      // 3.找到重复逻辑是什么？
      // - 如果当前节点不是叶子节点，递归得到下一个节点的sum
      // 4.自己调用自己
      int leftSum = dfs(root.left, sum);
      int rightSum = dfs(root.right, sum);
      // 5.返回
      return leftSum + rightSum;
    }
  }

  /**
   * 方法二：广度优先搜索
   * 自己编写的代码
   * @param root
   * @return
   */
  public static int sumNumbers2(TreeNode root) {
    if (root == null) {
      return 0;
    }
    // 新建一个list，用于保存从根节点到每个叶子节点对应的和
    Deque<Integer> resList = new LinkedBlockingDeque<>();

    // 新建队列 nodeQueue 用于存放将要遍历的节点
    Queue<TreeNode> nodeQueue = new LinkedBlockingDeque<>();
    // 新建队列 valNode 用于存放根节点到某节点的父节点之和
    Queue<Integer> valQueue = new LinkedBlockingDeque<>();
    nodeQueue.offer(root);
    valQueue.offer(0);
    while (!nodeQueue.isEmpty()) {
      TreeNode currNode = nodeQueue.poll();
      Integer currVal = valQueue.poll();
      int sum = currVal * 10 + currNode.val;
      // 如果当前节点是叶子节点,将根节点到当前节点的路径数字加到resList
      if (currNode.left == null && currNode.right == null) {
        resList.offerLast(sum);
      } else {
        if (currNode.left != null) {
          nodeQueue.offer(currNode.left);
          valQueue.offer(sum);
        }
        if (currNode.right != null) {
          nodeQueue.offer(currNode.right);
          valQueue.offer(sum);
        }
      }
    }
    if (resList.size() > 0) {
      int res = 0;
      for (Integer integer : resList) {
        res += integer;
      }
      return res;
    } else {
      return 0;
    }
  }
  /**
   * 方法二：广度优先搜索
   * LeetCode官方的代码
   * @param root
   * @return
   */
  public static int sumNumbers3(TreeNode root) {
    if (root == null) {
      return 0;
    }
    // 保存所有叶子节点对应的和（我自己的写法是将叶子节点对应的值加入了list，其实没必要浪费这个空间，官方的代码更好）
    int sum = 0;
    // 存放将要遍历的节点
    Queue<TreeNode> nodeQueue = new LinkedList<>();
    // 存放将要遍历的节点对应的sum值
    Queue<Integer> numQueue = new LinkedList<Integer>();
    nodeQueue.offer(root);
    numQueue.offer(root.val);
    while (!nodeQueue.isEmpty()) {
      TreeNode node = nodeQueue.poll();
      int num = numQueue.poll();
      TreeNode left = node.left, right = node.right;
      // 如果当前节点是叶子节点，将叶子节点的val加入 sum
      if (left == null && right == null) {
        sum += num;
      } else {
        // 当左右孩子不为空时，分别加入左右孩子，其对应的 num 为 num*10+left.val/right.val
        if (left != null) {
          nodeQueue.offer(left);
          numQueue.offer(num * 10 + left.val);
        }
        if (right != null) {
          nodeQueue.offer(right);
          numQueue.offer(num * 10 + right.val);
        }
      }
    }
    return sum;
  }

}
