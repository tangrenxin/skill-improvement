package d.binarytree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/9/14 01:21
 */
public class Test {

  public static void main(String[] args) {

    String str = "5,4,8,11,null,13,4,7,2,null,null,null,1";
//    String str = "[1,2,3]";
    NodeUtil nodeUtil = new NodeUtil();
    TreeNode root = nodeUtil.createTree(str);
    prevOrder(root);
  }

  //  先序遍历
  public static void prevOrder(TreeNode root) {
    if (root == null) {
      return;
    }
    System.out.print(root.val + ",");
    prevOrder(root.left);
    prevOrder(root.right);
  }

  // 中序遍历
  public void inOrder(TreeNode root) {
    if (root == null) {
      return;
    }
    inOrder(root.left);
    System.out.println(root.val + ",");
    inOrder(root.right);
  }

  // 后序遍历
  public void postOrder(TreeNode root) {
    if (root == null) {
      return;
    }
    postOrder(root.left);
    postOrder(root.right);
    System.out.print(root.val + ",");
  }

  // 层次遍历
  public void levelOrder(TreeNode root) {
    Queue<TreeNode> queue = new LinkedList<TreeNode>();
    queue.offer(root);
    while (!queue.isEmpty()) {
      TreeNode node = queue.poll();
      System.out.println(node);
      if (node.left != null) {
        queue.offer(node.left);
      }
      if (node.right != null) {
        queue.offer(node.right);
      }
    }
  }


  public ArrayList<ArrayList<Integer>> levelOrder2(TreeNode root) {
    ArrayList<ArrayList<Integer>> res = new ArrayList<>();
    if (root == null) {
      return res;
    }
    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    while (!queue.isEmpty()) {
      ArrayList<Integer> list = new ArrayList<>();
      int size = queue.size();
      for (int i = 0; i < size; i++) {
        TreeNode temp = queue.poll();
        list.add(temp.val);
        if (temp.left != null) {
          queue.add(temp.left);
        }
        if (temp.right != null) {
          queue.add(temp.right);
        }
      }

      // 如果是正序输出：
//      res.add(list);

      //不利用栈进行倒序,而是直接加入到0位置,实现了倒叙
      res.add(0, list);
    }
    return res;
  }


}
