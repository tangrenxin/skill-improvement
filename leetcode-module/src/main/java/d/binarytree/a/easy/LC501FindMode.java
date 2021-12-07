package d.binarytree.a.easy;

import d.binarytree.NodeUtil;
import d.binarytree.TreeNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Description:
 * 【501. 二叉搜索树中的众数】
 *
 * 给定一个有相同值的二叉搜索树（BST），找出 BST 中的所有众数（出现频率最高的元素）。
 *
 * 假定 BST 有如下定义：
 *
 *     结点左子树中所含结点的值小于等于当前结点的值
 *     结点右子树中所含结点的值大于等于当前结点的值
 *     左子树和右子树都是二叉搜索树
 *
 * 例如：
 * 给定 BST [1,null,2,2],
 *
 *    1
 *     \
 *      2
 *     /
 *    2
 *
 * 返回[2].
 *
 * 提示：如果众数超过1个，不需考虑输出顺序
 *
 * 进阶：你可以不使用额外的空间吗？（假设由递归产生的隐式调用栈的开销不被计算在内）
 *
 * @Author: tangrenxin
 * @Date: 2021/12/7 下午3:11
 */
public class LC501FindMode {

  public static void main(String[] args) {
    String str1 = "[1,2,2]";
    NodeUtil nodeUtil = new NodeUtil();
    TreeNode root = nodeUtil.createTree(str1);
    int[] mode = new LC501FindMode().findMode2(root);
    for (int i : mode) {
      System.out.println(i);
    }
  }


  /**
   * 方法一：广度优先搜索
   * @param root
   * @return
   */
  public static int[] findMode(TreeNode root) {
    if (root == null) {
      return null;
    }
    Queue<TreeNode> queue = new LinkedBlockingDeque<>();
    queue.offer(root);
    HashMap<Integer, Integer> map = new HashMap<>();
    while (!queue.isEmpty()) {
      TreeNode node = queue.poll();
      if (map.containsKey(node.val)) {
        map.put(node.val, map.get(node.val) + 1);
      } else {
        map.put(node.val, 1);
      }
      if (node.left != null) {
        queue.offer(node.left);
      }
      if (node.right != null) {
        queue.offer(node.right);
      }
    }
    Object[] values = map.values().toArray();
    Arrays.sort(values);
    int max = (int) values[values.length - 1];
    List<Integer> res = new LinkedList<>();
    for (Integer key : map.keySet()) {
      if (map.get(key) == max) {
        res.add(key);
      }
    }
    return res.stream().mapToInt(i->i).toArray();
  }


  /**
   * 官方实现方式：
   *
   *
   时间复杂度：O(n)O(n)O(n)。即遍历这棵树的复杂度。
   空间复杂度：O(n)O(n)O(n)。即递归的栈空间的空间代价。
   *
   *
   */
  List<Integer> answer = new ArrayList<>();
  int base, count, maxCount;

  public int[] findMode2(TreeNode root) {
    dfs(root);
    int[] mode = new int[answer.size()];
    for (int i = 0; i < answer.size(); ++i) {
      mode[i] = answer.get(i);
    }
    return mode;
  }

  public void dfs(TreeNode o) {
    if (o == null) {
      return;
    }
    dfs(o.left);
    update(o.val);
    dfs(o.right);
  }

  public void update(int x) {
    if (x == base) {
      ++count;
    } else {
      count = 1;
      base = x;
    }
    if (count == maxCount) {
      answer.add(base);
    }
    if (count > maxCount) {
      maxCount = count;
      answer.clear();
      answer.add(base);
    }
  }


}
