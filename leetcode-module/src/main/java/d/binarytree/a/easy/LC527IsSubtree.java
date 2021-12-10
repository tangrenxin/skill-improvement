package d.binarytree.a.easy;

import d.binarytree.TreeNode;

/**
 * @Description:
 * 572. 另一棵树的子树
 *
 * 给你两棵二叉树 root 和 subRoot 。检验 root 中是否包含和 subRoot 具有相同结构和节点值的子树。如果存在，返回 true ；否则，返回 false 。
 *
 * 二叉树 tree 的一棵子树包括 tree 的某个节点和这个节点的所有后代节点。tree 也可以看做它自身的一棵子树。
 *
 * 示例 1：
 *
 * 输入：root = [3,4,5,1,2], subRoot = [4,1,2]
 * 输出：true
 *
 * 示例 2：
 *
 * 输入：root = [3,4,5,1,2,null,null,null,null,0], subRoot = [4,1,2]
 * 输出：false
 *
 * 提示：
 *
 *     root 树上的节点数量范围是 [1, 2000]
 *     subRoot 树上的节点数量范围是 [1, 1000]
 *     -104 <= root.val <= 104
 *     -104 <= subRoot.val <= 104
 * @Author: tangrenxin
 * @Date: 2021/12/9 上午10:25
 */
public class LC527IsSubtree {

  public static void main(String[] args) {

  }

  public boolean isSubtree(TreeNode root, TreeNode subRoot) {
    return dfs(root, subRoot);
  }

  public boolean dfs(TreeNode s, TreeNode t) {
    if (s == null) {
      return false;
    }
    return check(s, t) || dfs(s.left, t) || dfs(s.right, t);
  }

  /**
   * 如果 root.val = subRoot.val 继续遍历子节点是否相同
   * @param s
   * @param t
   * @return
   */
  public boolean check(TreeNode s, TreeNode t) {
    if (s == null && t == null) {
      return true;
    }
    if (s == null || t == null || s.val != t.val) {
      return false;
    }
    return check(s.left, t.left) && check(s.right, t.right);
  }
}
