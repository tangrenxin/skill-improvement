package d.binarytree.a.easy;

import d.binarytree.NodeUtil;
import d.binarytree.TreeNode;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * 【94. 二叉树的中序遍历】
 *
 * 给定一个二叉树的根节点 root ，返回它的 中序 遍历。
 * @Author: tangrenxin
 * @Date: 2021/11/21 11:59
 */
public class LC94InorderTraversal {

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
    System.out.println(inorderTraversal(root));
  }

  /**
   * 方法一：深度优先遍历
   * @param root
   * @return
   */
  public static List<Integer> inorderTraversal(TreeNode root) {

    return dfs(root,new ArrayList<Integer>());
  }

  private static List<Integer> dfs(TreeNode root, ArrayList<Integer> list) {
    if (root == null) {
      return list;
    }
    dfs(root.left,list);
    list.add(root.val);
    dfs(root.right,list);
    return list;
  }
}
