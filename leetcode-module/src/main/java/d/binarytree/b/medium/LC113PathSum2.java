package d.binarytree.b.medium;

import d.binarytree.NodeUtil;
import d.binarytree.TreeNode;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Description:
 * 113. 路径总和 II
 *
 * 给你二叉树的根节点 root 和一个整数目标和 targetSum ，找出所有 从根节点到叶子节点 路径总和等于给定目标和的路径。
 *
 * 叶子节点 是指没有子节点的节点。
 *
 * @Author: tangrenxin
 * @Date: 2021/11/20 12:02
 */
public class LC113PathSum2 {


  /**
   * 注意到本题的要求是，找到所有满足从「根节点」到某个「叶子节点」经过的路径上的节点之和等于目标和的路径。
   * 核心思想是对树进行一次遍历，在遍历时记录从根节点到当前节点的路径和，以防止重复计算。
   *
   * 方法一：深度优先搜索
   * 采用深度优先的方式，枚举每一条从根节点到叶子节点的路径。当我们遍历到叶子节点，且此时路径和恰好为目标和时，
   * 我们就找到了一条满足条件的路径。
   *
   * 方法二：广度优先搜索
   * 采用广度优先搜索的方式，遍历这棵树。当我们遍历到叶子节点，且此时路径和恰好为目标和时，我们就找到了一条满足条件的路径
   * 为了节省空间，我们使用哈希表记录树中的每一个节点的父节点。每次找到一个满足条件的节点，我们就从该节点触发不断想父节点
   * 迭代，即可还原出从根节点到当前节点的路径。
   *
   * @param args
   */


  public static void main(String[] args) {
    String str = "[5,4,8,11,null,13,4,7,2,null,null,null,null,5,1]";
    NodeUtil nodeUtil = new NodeUtil();
    TreeNode root = nodeUtil.createTree(str);
    List<List<Integer>> lists = pathSum(root, 22);
    System.out.println(lists);
    System.out.println(pathSum2(root, 22));

  }

  /**
   * 方法一：深度优先搜索-递归
   *
   * @param root
   * @param targetSum
   * @return
   */
  public static List<List<Integer>> pathSum(TreeNode root, int targetSum) {
    // 用于存放结果路径
    List<List<Integer>> ret = new LinkedList<List<Integer>>();
    // 用于存放从根节点到当前节点的路径
    Deque<Integer> path = new LinkedList<>();
    dfs(root, targetSum, ret, path);
    return ret;
  }

  // 中序遍历
  public static void dfs(TreeNode root, int targetSum, List<List<Integer>> ret,
      Deque<Integer> path) {
    if (root == null) {
      return;
    }
    // 将当前节点插入到当前路径中
    path.offerLast(root.val);
    if (root.left == null && root.right == null && targetSum == root.val) {
      // 如果是叶子节点，并且路径之和等于 targetSum, 将根节点到当前节点的路径添加到结果list中
      ret.add(new LinkedList<>(path));
    }
    // 继续遍历左孩子
    dfs(root.left, targetSum - root.val, ret, path);
    // 继续遍历右孩子
    dfs(root.right, targetSum - root.val, ret, path);
    // 根 左 右 根节点遍历完毕，移除路径中的最后一个值，回归到父节点
    path.pollLast();
  }

  /**
   *  方法二：广度优先搜索
   */

  public static List<List<Integer>> pathSum2(TreeNode root, int targetSum) {

    // 用于存放结果路径
    List<List<Integer>> ret = new LinkedList<List<Integer>>();
    // 用于存放从根节点到当前节点的路径
    HashMap<TreeNode, TreeNode> map = new HashMap<>();

    if (root == null) {
      return ret;
    }

    // 新建队列 nodeQueue 用于存放将要遍历的节点
    Queue<TreeNode> nodeQueue = new LinkedBlockingDeque<>();
    // 新建队列 valNode 用于存放根节点到某节点的路径之和
    Queue<Integer> valQueue = new LinkedBlockingDeque<>();
    nodeQueue.offer(root);
    valQueue.offer(root.val);

    while (!nodeQueue.isEmpty()) {
      TreeNode currNode = nodeQueue.poll();
      Integer currVal = valQueue.poll();
      if (currNode.left == null && currNode.right == null) {
        // 将当前节点的路径添加到 ret
        if (currVal == targetSum) {
          getPath(currNode, ret, map);
        }
      } else {
        if (currNode.left != null) {
          map.put(currNode.left, currNode);
          nodeQueue.offer(currNode.left);
          valQueue.offer(currNode.left.val + currVal);
        }
        if (currNode.right != null) {
          map.put(currNode.right, currNode);
          nodeQueue.offer(currNode.right);
          valQueue.offer(currNode.right.val + currVal);
        }
      }
    }
    return ret;
  }

  private static void getPath(TreeNode node, List<List<Integer>> ret,
      HashMap<TreeNode, TreeNode> map) {
    LinkedList<Integer> list = new LinkedList<>();
    while (node != null) {
      list.add(node.val);
      node = map.get(node);
    }
    // 添加完后倒序排列
    Collections.reverse(list);
    ret.add(list);

  }


}
