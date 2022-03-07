package d.binarytree;

import java.util.Stack;

/**
 * @Description:
 * 二叉树基本的查询方法
 * 1.递归实现前中后序遍历
 * 2.迭代实现前中后序遍历
 * 3.广度优先遍历实现层序遍历
 *
 * 技巧：
 * 二叉树的迭代遍历，借助 栈
 * 二叉树的层序遍历，借助 队列
 *
 * @Author: tangrenxin
 * @Date: 2022/3/4 16:38
 */
public class BasicQueryCase {

  public static void main(String[] args) {
    int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    TreeNode root = NodeUtil.createBinTree(array);

    System.out.println("深度优先-先序遍历");
    System.out.print("递归：");
    prevOrder(root);
    System.out.print("\n迭代：");
    prevOrder2(root);
    System.out.println("\n==========================");

    System.out.println("深度优先-中序遍历");
    System.out.print("递归：");
    inOrder(root);
    System.out.print("\n迭代：");
    inOrder2(root);
    System.out.println("\n==========================");

    System.out.println("深度优先-后序遍历");
    System.out.print("递归：");
    postOrder(root);
    System.out.print("\n迭代：");
    postOrder2(root);
    System.out.println("\n==========================");

//    System.out.println("广度优先-层序遍历");
//    levelOrder(root);
//    System.out.println("\n广度优先-层序遍历 V2");
//    levelOrderV2(root);
  }

  /**
   * 后序遍历 递归实现
   * @param root
   */
  private static void postOrder(TreeNode root) {
    if (root == null) {
      return;
    }
    postOrder(root.left);
    postOrder(root.right);
    System.out.print(root.val + " ");
  }

  /**
   * 后序遍历 迭代实现
   * @param root
   */
  private static void postOrder2(TreeNode root) {

  }

  /**
   * 中序遍历 递归实现
   * @param root
   */
  private static void inOrder(TreeNode root) {
    if (root == null) {
      return;
    }
    inOrder(root.left);
    System.out.print(root.val + " ");
    inOrder(root.right);
  }

  /**
   * 中序遍历 迭代实现
   * 1.持续迭代根节点的左孩子，将根节点及左孩子入栈
   * 2.当根节点无左孩子时，弹出栈顶节点，输出节点值，访问右节点，重复执行1-3操作
   * @param root
   */
  private static void inOrder2(TreeNode root) {
    Stack<TreeNode> stack = new Stack<>();
    TreeNode currNode = root;
    while (currNode != null || !stack.isEmpty()) {
      // 持续迭代左孩子，并入栈
      while (currNode != null) {
        stack.push(currNode);
        currNode = currNode.left;
      }

      // 如果节点无左孩子，弹出栈顶节点并输出节点值，访问右孩子
      if (!stack.isEmpty()) {
        // 弹出栈顶节点
        TreeNode treeNode = stack.pop();
        // 输出栈顶节点值
        System.out.print(treeNode.val + " ");
        // 访问右节点
        currNode = treeNode.right;
      }
    }
  }


  /**
   * 先序遍历 递归实现
   * @param root
   */
  private static void prevOrder(TreeNode root) {
    if (root == null) {
      return;
    }
    System.out.print(root.val + " ");
    prevOrder(root.left);
    prevOrder(root.right);
  }

  /**
   * 先序遍历 迭代实现
   * 1.迭代访问节点,作为根节点,输出当前节点值
   * 2.根节点入栈,访问左孩子,直到左孩子为null
   * 3.此时该根节点无左孩子,栈中弹出根节点,访问弹出节点的右孩子,重复1-3步
   * @param root
   */
  private static void prevOrder2(TreeNode root) {

    // 定义一个栈
    Stack<TreeNode> stack = new Stack<>();
    TreeNode currNode = root;
    // 循环条件：当前节点不为空，或者栈中不为空（当前节点为空时，弹出栈顶元素）
    while (currNode != null || !stack.isEmpty()) {
      // 迭代持续遍历节点的左孩子
      while (currNode != null) {
        // 打印根节点
        System.out.print(currNode.val + " ");
        // 根节点入栈
        stack.push(currNode);
        // 持续访问左孩子
        currNode = currNode.left;
      }

      // 当节点没有左孩子时，弹出栈顶节点，访问右孩子
      if (!stack.isEmpty()) {
        TreeNode treeNode = stack.pop();
        currNode = treeNode.right;
      }
    }
  }

}
