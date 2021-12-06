package d.binarytree.a.easy;

import d.binarytree.TreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description:
 * 【235. 二叉搜索树的最近公共祖先】
 *
 * 给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。
 *
 * 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，
 * 满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
 *
 * 例如，给定如下二叉搜索树:  root = [6,2,8,0,4,7,9,null,null,3,5]
 *
 * 示例 1:
 *
 * 输入: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
 * 输出: 6
 * 解释: 节点 2 和节点 8 的最近公共祖先是 6。
 *
 * 示例 2:
 *
 * 输入: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
 * 输出: 2
 * 解释: 节点 2 和节点 4 的最近公共祖先是 2, 因为根据定义最近公共祖先节点可以为节点本身。
 * @Author: tangrenxin
 * @Date: 2021/12/6 下午12:37
 */
public class LC235LowestCommonAncestor {

  public static void main(String[] args) {

  }

  public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    // 得到两个节点的路径，存到list中，对比两个list，
    // 1.分叉点就是公共祖先
    // 2.如果一个其中一个list结束，且还没有分叉，那这个节点就是他们的公共祖先
    Deque<TreeNode> tmpPath = new LinkedList<>();
    // p节点的路径
    List<TreeNode> pList = dfs(root, p, tmpPath, new ArrayList<TreeNode>());
    tmpPath.clear();
    List<TreeNode> qList = dfs(root, q, tmpPath, new ArrayList<TreeNode>());

    List<TreeNode> shortList = pList.size() < qList.size() ? pList : qList;
    List<TreeNode> longList = pList.size() < qList.size() ? qList : pList;
    System.out.println(shortList);
    System.out.println(longList);
    if(shortList.size() == 0){
      return null;
    }

    int i = 0;
    while (i < shortList.size()) {
      if (shortList.get(i) == longList.get(i)) {
        i++;
        continue;
      } else {
        return shortList.get(i - 1);
      }
    }
    // shortList 最后一个节点 是 longList 前面的节点
    if(shortList.get(i-1) == longList.get(i-1)){
      return shortList.get(i-1);
    }
    return null;
  }

  private static List<TreeNode> dfs(TreeNode root, TreeNode node, Deque<TreeNode> tmpPath,
      ArrayList<TreeNode> list) {
    if (root == null || node == null) {
      return list;
    }
    tmpPath.offerLast(root);
    if (root.val == node.val) {
      System.out.println(tmpPath);
      list.addAll(tmpPath);
      return list;
    } else {
      dfs(root.left, node, tmpPath, list);
      dfs(root.right, node, tmpPath, list);
      tmpPath.pollLast();
    }
    return list;
  }
}
