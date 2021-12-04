package d.binarytree.a.easy;

import d.binarytree.NodeUtil;
import d.binarytree.TreeNode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @Description:
 * 145. 二叉树的后序遍历
 *
 * 给定一个二叉树，返回它的 后序 遍历。
 *
 * 示例:
 *
 * 输入: [1,null,2,3]
 *    1
 *     \
 *      2
 *     /
 *    3
 *
 * 输出: [3,2,1]
 *
 * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
 *
 * @Author: tangrenxin
 * @Date: 2021/12/3 下午4:42
 */
public class LC145PostorderTraversal {

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
    System.out.println(postorderTraversal(root));
    System.out.println(postorderTraversal2(root));

  }

  /**
   * 方法一：递归实现方式
   * @param root
   * @return
   */
  public static List<Integer> postorderTraversal(TreeNode root) {
    return dfs(root, new ArrayList<Integer>());
  }

  private static List<Integer> dfs(TreeNode root, ArrayList<Integer> list) {
    if (root == null) {
      return list;
    }
    dfs(root.left, list);
    dfs(root.right, list);
    list.add(root.val);
    return list;
  }

  /**
   * 方法二：迭代实现方式
   *     1.根节点入栈
   *     2.将根节点的左子树入栈，直到最左，没有左孩子为止
   *     3.得到栈顶元素的值，先不访问，判断栈顶元素是否存在右孩子，如果存在并且没有被访问，则将右孩子入栈，否则，就访问栈顶元素
   * @param root
   * @return
   */
  public static List<Integer> postorderTraversal2(TreeNode root) {
    List<Integer> list = new LinkedList<>();
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
          list.add(treeNode.val);
          // 标记本次访问的节点
          pre = treeNode;
          treeNode = null;
        } else {
          // 4.存在没有被访问的右孩子
          treeNode = treeNode.right;
        }
      }
    }
    return list;
  }
}
