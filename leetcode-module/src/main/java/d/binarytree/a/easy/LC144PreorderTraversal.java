package d.binarytree.a.easy;

import d.binarytree.NodeUtil;
import d.binarytree.TreeNode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @Description:
 * 144. 二叉树的前序遍历
 *
 * 给你二叉树的根节点 root ，返回它节点值的 前序 遍历。
 *
 * 示例 1：
 *
 * 输入：root = [1,null,2,3]
 * 输出：[1,2,3]
 *
 * 示例 2：
 *
 * 输入：root = []
 * 输出：[]
 *
 * 示例 3：
 *
 * 输入：root = [1]
 * 输出：[1]
 *
 * 示例 4：
 *
 * 输入：root = [1,2]
 * 输出：[1,2]
 *
 * 示例 5：
 *
 * 输入：root = [1,null,2]
 * 输出：[1,2]
 *
 *
 *
 * 提示：
 *
 *     树中节点数目在范围 [0, 100] 内
 *     -100 <= Node.val <= 100
 *
 * 进阶：递归算法很简单，你可以通过迭代算法完成吗？
 *
 * @Author: tangrenxin
 * @Date: 2021/12/3 下午4:42
 */
public class LC144PreorderTraversal {


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
    System.out.println(preorderTraversal(root));
    System.out.println(preorderTraversal2(root));

  }

  /**
   * 方法一：深度优先遍历
   * 根 左 右
   * @param root
   * @return
   */
  public static List<Integer> preorderTraversal(TreeNode root) {

    return dfs(root, new ArrayList<Integer>());
  }

  private static List<Integer> dfs(TreeNode root, ArrayList<Integer> list) {
    if (root == null) {
      return list;
    }
    list.add(root.val);
    dfs(root.left, list);
    dfs(root.right, list);
    return list;
  }

  /**
   * 方法二：非递归先序遍历
   * @param root
   */
  private static List<Integer> preorderTraversal2(TreeNode root) {
    List<Integer> list = new LinkedList<>();
    Stack<TreeNode> stack = new Stack<>();
    TreeNode treeNode = root;
    while (treeNode != null || !stack.isEmpty()) {
      // 迭代访问节点的左孩子 并入栈
      while (treeNode != null) {
        // 输出根节点
        list.add(treeNode.val);
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
    return list;
  }

}
