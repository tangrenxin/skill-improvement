package d.binarytree.b.medium;

import com.sun.scenario.effect.Brightpass;
import com.sun.tools.corba.se.idl.constExpr.ShiftRight;
import d.binarytree.NodeUtil;
import d.binarytree.TreeNode;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description:
 * 【437. 路径总和 III】
 * 题目链接：https://leetcode-cn.com/problems/path-sum-iii/
 *
 * 给定一个二叉树的根节点 root ，和一个整数 targetSum ，求该二叉树里节点值之和等于 targetSum 的 路径 的数目。
 *
 * 路径 不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。
 *
 * 示例 1：
 *
 * 输入：root = [10,5,-3,3,2,null,11,3,-2,null,1], targetSum = 8
 * 输出：3
 * 解释：和等于 8 的路径有 3 条，如图所示。
 *
 * 示例 2：
 *
 * 输入：root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
 * 输出：3
 *
 * 提示:
 *
 *     二叉树的节点个数的范围是 [0,1000]
 *     -109 <= Node.val <= 109
 *     -1000 <= targetSum <= 1000
 * @Author: tangrenxin
 * @Date: 2021/11/28 下午2:08
 */
public class LC437PathSum3 {

  /**
   * 方法一：深度优先搜索
   * 思路与算法
   * 我们首先想到的解法是穷举所有的可能，我们访问每一个节点 node，检测以 node 为起始节点(当做root节点)且向下延深的路径
   * 有多少种。我们递归遍历每一个节点的所有可能的路径，然后将这些路径数目加起来即为返回结果。
   *
   *
   */

  public static void main(String[] args) {
    String str = "[10,5,-3,3,2,null,11,3,-2,null,1]";
    NodeUtil nodeUtil = new NodeUtil();
    TreeNode root = nodeUtil.createTree(str);
    int i = pathSum(root, 8);
    System.out.println(i);
    System.out.println("++++++++++++++++++");
    System.out.println(pathSum2(root, 8));

  }

  /**
   * LeetCode 官方的算法
   * @param root
   * @param targetSum
   * @return
   */
  public static int pathSum(TreeNode root, int targetSum) {
    if (root == null) {
      return 0;
    }
    // 把当前节点让做 root ，就变成了 路径求和1
    int ret = rootSum(root, targetSum);
    // 分别将左孩子和右孩子当做 root ，得到计数
    ret += pathSum(root.left, targetSum);
    ret += pathSum(root.right, targetSum);
    return ret;
  }

  /**
   * 子流程：就是 路径求和1 的深度优先实现算法
   * @param root
   * @param targetSum
   * @return
   */
  public static int rootSum(TreeNode root, int targetSum) {
    int ret = 0;

    if (root == null) {
      return 0;
    }
    int val = root.val;
    if (val == targetSum) {
      ret++;
    }

    ret += rootSum(root.left, targetSum - val);
    ret += rootSum(root.right, targetSum - val);
    return ret;
  }

  /**
   * 基于官方的算法，我们来实现一个保存所有路径的算法
   * 还有点问题
   */
  public static int pathSum2(TreeNode root, int targetSum) {
    List<List<Integer>> ret = getPathSumDfs(root, targetSum);
    for (List<Integer> integers : ret) {
      System.out.println(integers);
    }
    return ret.size();
  }

  private static List<List<Integer>> getPathSumDfs(TreeNode root, int targetSum) {

    List<List<Integer>> ret = new LinkedList<>();
    Deque<Integer> path = new LinkedList<>();
    if(root == null){
      return ret;
    }
    List<List<Integer>> rootRet = getRootSumDfs(root, targetSum, ret, path);
    List<List<Integer>> leftRet = getPathSumDfs(root.left, targetSum);
    List<List<Integer>> rightRet = getPathSumDfs(root.right, targetSum);
    ret.addAll(rootRet);
    ret.addAll(leftRet);
    ret.addAll(rightRet);

    return ret;
  }

  private static List<List<Integer>> getRootSumDfs(TreeNode root, int targetSum,
      List<List<Integer>> ret, Deque<Integer> path) {
    if (root == null) {
      return ret;
    }
    path.offerLast(root.val);
    int val = root.val;
    if (val == targetSum) {
      ret.add(new LinkedList<>(path));
    }
    getRootSumDfs(root.left, targetSum - val, ret, path);
    getRootSumDfs(root.right, targetSum - val, ret, path);
    path.pollLast();
    return ret;
  }


  /**
   * 方法一：深度优先遍历
   * 这是自己实现的方法，运行结果超时
   * @param root
   * @param targetSum
   * @return
   */
  public static int pathSum0(TreeNode root, int targetSum) {
    // 题目虽然没有要求打印路径，但我做了个变种，保留了路径
    // 用于存放结果路径
    List<List<Integer>> ret = new LinkedList<>();
    // 用于存放从根节点到当前节点的路径
    Deque<Integer> path = new LinkedList<>();
    // 用于存放从根节点到当前节点的和
    int sum = 0;
    dfs(root, targetSum, sum, ret, path);
    // 打印路径
    for (List<Integer> integers : ret) {
      System.out.println(integers);
    }
    return ret.size();
  }

  private static void dfs(TreeNode root, int targetSum, int sum, List<List<Integer>> ret,
      Deque<Integer> path) {
    // 1.找到大问题是什么？--找到从根节点到当前节点(可以不从根节点开始)的和为 targetSum 的路径
    // 2.找到最简单的问题是什么？满足最简单问题时应该做什么？--root==null，直接返回
    if (root == null) {
      return;
    }
    path.offerLast(root.val);
    sum += root.val;
    // 3.找到重复逻辑是什么？--判断当前path里的和是否等于 targetSum，如果不等，移除根节点(path)中的第一个节点判断，
    // 直到path没有元素，再执行下一节点
    // 定义一个 tmpPath 用于循环
    Deque<Integer> tmpPath = new LinkedList<>(path);
    int tmpSum = sum;
    while (!tmpPath.isEmpty()) {
      if (tmpSum == targetSum) {
        // add path
        ret.add(new LinkedList<>(tmpPath));
      }
      // 移除第一个元素
      int first = tmpPath.pollFirst();
      tmpSum -= first;
    }
    // 4.自己调用自己
    // 5.返回结果
    dfs(root.left, targetSum, sum, ret, path);
    dfs(root.right, targetSum, sum, ret, path);
    path.pollLast();
  }
}
