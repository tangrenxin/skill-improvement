package d.binarytree.a.easy;

import d.binarytree.NodeUtil;
import d.binarytree.TreeNode;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import javax.print.attribute.standard.MediaSize.NA;

/**
 * @Description:
 * 【101. 对称二叉树】
 *
 * 给定一个二叉树，检查它是否是镜像对称的。
 *
 * 例如，二叉树 [1,2,2,3,4,4,3] 是对称的。
 *
 *     1
 *    / \
 *   2   2
 *  / \ / \
 * 3  4 4  3
 *
 * 但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的:
 *
 *     1
 *    / \
 *   2   2
 *    \   \
 *    3    3
 *
 * 进阶：
 * 你可以运用递归和迭代两种方法解决这个问题吗？  能
 *
 * @Author: tangrenxin
 * @Date: 2021/12/2 上午11:23
 */
public class LC101IsSymmetric {

  public static void main(String[] args) {
    String str1 = "[1,2,2,3,4,4,3]";
    NodeUtil nodeUtil = new NodeUtil();
    TreeNode root = nodeUtil.createTree(str1);
    System.out.println(isSymmetric(root));
    System.out.println(isSymmetric2(root));
  }

  /**
   * 方法一：深度优先
   * 思路和算法
   *
   * 如果一个树的左子树与右子树镜像对称，那么这个树是对称的。
   *
   * 因此，该问题可以转化为：两个树在什么情况下互为镜像？
   *
   * 如果同时满足下面的条件，两个树互为镜像：
   *
   *  - 它们的两个根结点具有相同的值
   *  - 每个树的右子树都与另一个树的左子树镜像对称
   *
   * 我们可以实现这样一个递归函数，通过「同步移动」两个指针的方法来遍历这棵树，p 指针和 q 指针一开始都指向这棵树的根，
   * 随后 p 右移时，q 左移，p 左移时，q 右移。每次检查当前 p 和 q 节点的值是否相等，
   * 如果相等再判断左右子树是否对称。
   *
   * @param root
   * @return
   */
  public static boolean isSymmetric(TreeNode root) {
    return dfs(root, root);
  }

  private static boolean dfs(TreeNode p, TreeNode q) {
    // 1.最大的问题?判断两棵树是否相等
    // 2.最小问题
    if (p == null || q == null) {
      // 如果两树都为null，返回true
      return (p == null && q == null) ? true : false;
    }
    // 如果两个节点的值不相等，false
    if (p.val != q.val) {
      return false;
    }
    // 3.重复逻辑
    // 4.自己调自己
    // 5.return
    // 移动指针，一个向左，一个向右
    return dfs(p.left, q.right) && dfs(p.right, q.left);
  }

  /**
   * 方法二：广度优先
   * 每一层的节点val，应该互为镜像 这个方法存在局限，没过
   * 官方：
   * 「方法一」中我们用递归的方法实现了对称性的判断，那么如何用迭代的方法实现呢？首先我们引入一个队列，
   * 这是把递归程序改写成迭代程序的常用方法。初始化时我们把根节点入队两次。每次提取两个结点并比较它们的值
   * （队列中每两个连续的结点应该是相等的，而且它们的子树互为镜像），然后将两个结点的左右子结点按相反的顺序插入队列中。
   * 当队列为空时，或者我们检测到树不对称（即从队列中取出两个不相等的连续结点）时，该算法结束。
   *
   * @param root
   * @return
   */
  public static boolean isSymmetric2(TreeNode root) {
    return check(root, root);
  }

  public static boolean check(TreeNode u, TreeNode v) {
    Queue<TreeNode> q = new LinkedList<TreeNode>();
    q.offer(u);
    q.offer(v);
    while (!q.isEmpty()) {
      u = q.poll();
      v = q.poll();
      if (u == null && v == null) {
        continue;
      }
      if ((u == null || v == null) || (u.val != v.val)) {
        return false;
      }

      q.offer(u.left);
      q.offer(v.right);

      q.offer(u.right);
      q.offer(v.left);
    }
    return true;
  }

}
