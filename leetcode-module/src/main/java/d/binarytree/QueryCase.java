package d.binarytree;

import java.nio.file.StandardWatchEventKinds;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @Description:
 * 二叉树基本查询
 * @Author: tangrenxin
 * @Date: 2021/10/26 19:54
 */
public class QueryCase {


  public static void main(String[] args) {
    int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    TreeNode root = NodeUtil.createBinTree(array);
    System.out.println("深度优先-先序遍历");
    System.out.print("递归：");
    prevOrder(root);
    System.out.print("\n非归：");
    prevOrder2(root);
    System.out.println("\n深度优先-中序遍历");
    System.out.print("递归：");
    inOrder(root);
    System.out.print("\n非归：");
    inOrder2(root);
    System.out.println("\n深度优先-后序遍历");
    System.out.print("递归：");
    postOrder(root);
    System.out.print("\n非归：");
    postOrder2(root);
//    System.out.println("\n广度优先-层序遍历");
//    levelOrder(root);
//    System.out.println("\n广度优先-层序遍历 V2");
//    levelOrderV2(root);
  }

  /**
   * 非递归后序遍历
   *     1.根节点入栈
   *     2.将根节点的左子树入栈，直到最左，没有左孩子为止
   *     3.得到栈顶元素的值，先不访问，判断栈顶元素是否存在右孩子，如果存在并且没有被访问，则将右孩子入栈，否则，就访问栈顶元素
   * @param root
   */
  private static void postOrder2(TreeNode root) {
    Stack<TreeNode> stack = new Stack<>();
    // 当前节点
    TreeNode treeNode = root;
    // 上一次访问的节点
    TreeNode pre = null;

    while (treeNode != null || !stack.isEmpty()) {
      // 1.根节点及其左孩子入栈
      while (treeNode != null) {
        stack.push(treeNode);
        treeNode = treeNode.left;
      }

      if (!stack.isEmpty()) {
        // 2.获取栈顶的值但是不弹出
        treeNode = stack.peek();
        // 3.没有右孩子，或者右孩子已经被访问过
        if (treeNode.right == null || treeNode.right == pre) {
          // 栈顶元素出栈
          treeNode = stack.pop();
          System.out.print(treeNode.val+",");
          // 标记本次访问的节点
          pre = treeNode;
          treeNode = null;
        } else {
          // 4.存在没有被访问的右孩子
          treeNode = treeNode.right;
        }
      }
    }
  }

  /**
   * 非递归中序遍历
   * @param root
   */
  private static void inOrder2(TreeNode root) {
    Stack<TreeNode> stack = new Stack<>();
    TreeNode treeNode = root;
    while (treeNode != null || !stack.isEmpty()) {
      // 1.将根节点入栈
      // 2.所有的左节点入栈
      while (treeNode != null) {
        stack.push(treeNode);
        treeNode = treeNode.left;
      }
      // 3.该节点没有左孩子了
      if (!stack.isEmpty()) {
        treeNode = stack.pop();
        System.out.print(treeNode.val + ",");
        treeNode = treeNode.right;

      }

    }
  }

  /**
   * 非递归先序遍历
   * @param root
   */
  private static void prevOrder2(TreeNode root) {
    Stack<TreeNode> stack = new Stack<>();
    TreeNode treeNode = root;
    while (treeNode != null || !stack.isEmpty()) {
      // 迭代访问节点的左孩子 并入栈
      while (treeNode != null) {
        // 输出根节点
        System.out.print(treeNode.val + ",");
        // 根节点入栈
        stack.push(treeNode);
        // 下一个左孩子
        treeNode = treeNode.left;
      }
      // 如果节点没有左孩子，则弹出栈顶的节点，访问右孩子
      if (!stack.isEmpty()) {
        treeNode = stack.pop();
        treeNode = treeNode.right;
      }
    }
  }

  /**
   * 广度优先-层序遍历V2
   * 输出格式为：
   * [
   *     [1],
   *     [2, 3],
   *     [4, 5, 6, 7]
   * ]
   * @param root
   */
  private static void levelOrderV2(TreeNode root) {
    // 借助队列结构
    Queue<TreeNode> queue = new LinkedList<>();
    // 将该层的所有节点加入队列
    queue.offer(root);
    while (!queue.isEmpty()) {
      int size = queue.size();
      for (int i = 0; i < size; i++) {
        TreeNode node = queue.poll();
        System.out.print(node.val + ",");
        if (node.left != null) {
          queue.offer(node.left);
        }

        if (node.right != null) {
          queue.offer(node.right);
        }
      }
      System.out.println();
    }
  }

  /**
   * 广度优先-层序遍历
   * @param root
   */
  private static void levelOrder(TreeNode root) {
    // 借助队列结构
    Queue<TreeNode> queue = new LinkedList<>();
    // 将该层的所有节点加入队列
    queue.offer(root);
    while (!queue.isEmpty()) {
      TreeNode node = queue.poll();
      System.out.print(node.val + ",");
      if (node.left != null) {
        queue.offer(node.left);
      }

      if (node.right != null) {
        queue.offer(node.right);
      }
    }
  }

  /**
   * 深度优先-后序遍历
   * @param root
   */
  private static void postOrder(TreeNode root) {
    if (root == null) {
      return;
    }
    postOrder(root.left);
    postOrder(root.right);
    System.out.print(root.val + ",");
  }

  /**
   * 深度优先-中序遍历
   * @param root
   */
  private static void inOrder(TreeNode root) {
    if (root == null) {
      return;
    }
    inOrder(root.left);
    System.out.print(root.val + ",");
    inOrder(root.right);
  }

  /**
   * 深度优先-先序遍历
   * @param root
   */
  private static void prevOrder(TreeNode root) {
    if (root == null) {
      return;
    }
    System.out.print(root.val + ",");
    prevOrder(root.left);
    prevOrder(root.right);
  }

}
