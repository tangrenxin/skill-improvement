package c.sort.z.base.a.innersort;

import d.binarytree.TreeNode;
import java.util.Random;
import java.util.Stack;

/**
 * @Description:
 *【二叉树排序】
 * 二叉树排序的基本原理：先构建一颗空树，使用第一个元素作为根节点，如果之后的元素比第一个小，则放到左子树，否则放到右子树，之后按中序遍历。
 *
 * 时间复杂度：
 * @Author: tangrenxin
 * @Date: 2021/11/1 13:41
 */
public class C07BinaryTreeSort {

  public static void main(String[] args) {
    Random rand = new Random();
    int[] nums = new int[20];
    System.out.print("初始数组--遍历：");
    for (int i = 0; i < 20; i++) {
      // 生成 20个 [0 - 999] 的随机数
      nums[i] = rand.nextInt(1000);
      System.out.print(nums[i] + ",");
    }
    System.out.println();
    // 创建 二叉排序树
    TreeNode root = createSortTree(nums);

    // 中序遍历数组 递归
    System.out.print("递归--中序遍历：");
    inOrder(root);

    // 中序遍历数组 非递归
    System.out.print("\n非归--中序遍历：");
    inOrder2(root);

  }

  private static void inOrder2(TreeNode root) {
    Stack<TreeNode> stack = new Stack<>();
    TreeNode moveRoot = root;
    while (moveRoot != null || !stack.isEmpty()) {
      // 1.将根节点入栈
      // 2.所有的左节点入栈
      while (moveRoot != null) {
        stack.push(moveRoot);
        moveRoot = moveRoot.left;
      }
      // 3.该节点没有左孩子了 弹出 栈顶元素
      if (!stack.isEmpty()) {
        moveRoot = stack.pop();
        System.out.print(moveRoot.val + ",");
        moveRoot = moveRoot.right;
      }

    }
  }

  private static void inOrder(TreeNode root) {
    if (root == null) {
      return;
    }
    inOrder(root.left);
    System.out.print(root.val + ",");
    inOrder(root.right);
  }

  private static TreeNode createSortTree(int[] nums) {
    TreeNode root = null;
    TreeNode moveRoot = null;
    for (int num : nums) {
      if (root == null) {
        root = new TreeNode();
        root.val = num;
        root.left = null;
        root.right = null;
        moveRoot = root;
        continue;
      }
      while (moveRoot != null) {
        // 比当前节点小
        if (num < moveRoot.val) {
          // 判断是否有左孩子，没有就插入左，结束循环
          // 有的话比较左孩子
          if (moveRoot.left == null) {
            TreeNode node = new TreeNode(num);
            moveRoot.left = node;
            moveRoot = root;
            break;
          } else {
            moveRoot = moveRoot.left;
          }
        } else {
          // 比当前数大
          // 判断是否有右孩子，没有就插入右，结束循环
          if (moveRoot.right == null) {
            TreeNode node = new TreeNode(num);
            moveRoot.right = node;
            moveRoot = root;
            break;
          } else {
            moveRoot = moveRoot.right;
          }
        }
      }
    }
    return root;
  }

}
