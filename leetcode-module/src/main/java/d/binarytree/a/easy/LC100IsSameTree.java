package d.binarytree.a.easy;

import d.binarytree.NodeUtil;
import d.binarytree.TreeNode;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Description:
 * 100. 相同的树
 *
 * 给你两棵二叉树的根节点 p 和 q ，编写一个函数来检验这两棵树是否相同。
 *
 * 如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。
 *
 * 示例 1：
 *
 * 输入：p = [1,2,3], q = [1,2,3]
 * 输出：true
 *
 * 示例 2：
 *
 * 输入：p = [1,2], q = [1,null,2]
 * 输出：false
 *
 * 示例 3：
 *
 * 输入：p = [1,2,1], q = [1,1,2]
 * 输出：false
 *
 * 提示：
 *
 *     两棵树上的节点数目都在范围 [0, 100] 内
 *     -104 <= Node.val <= 104
 * @Author: tangrenxin
 * @Date: 2021/12/2 上午10:49
 */
public class LC100IsSameTree {

  public static void main(String[] args) {
    String str1 = "[1,2,3]";
    String str2 = "[1,2,3]";
//    String str1 = "[1]";
//    String str2 = "[1,2,null]";
    NodeUtil nodeUtil = new NodeUtil();
    TreeNode root1 = nodeUtil.createTree(str1);
    TreeNode root2 = nodeUtil.createTree(str2);
    System.out.println(isSameTree(root1, root2));
    System.out.println(isSameTree2(root1, root2));

  }

  /**
   * 方法一：深度优先搜索
   * 递归
   * 判断当前节点值是否相等
   * 判断节点的左后孩子有无情况是否相等
   * 以上都相等，递归，否则返回false
   * @param p
   * @param q
   * @return
   */
  public static boolean isSameTree(TreeNode p, TreeNode q) {
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
    return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
  }

  /**
   * 方法二：广度优先搜索
   * @param p
   * @param q
   * @return
   */
  public static boolean isSameTree2(TreeNode p, TreeNode q) {
    if (p == null || q == null) {
      // 如果两树都为null，返回true
      return (p == null && q == null) ? true : false;
    }
    Queue<TreeNode> queueP = new LinkedBlockingDeque<>();
    Queue<TreeNode> queueQ = new LinkedBlockingDeque<>();
    queueP.offer(p);
    queueQ.offer(q);
    while (!queueP.isEmpty() && !queueQ.isEmpty()) {
      TreeNode nodeP = queueP.poll();
      TreeNode nodeQ = queueQ.poll();
      if (nodeP.val != nodeQ.val) {
        return false;
      }
      if (nodeP.left != null && nodeQ.left != null) {
        queueP.offer(nodeP.left);
        queueQ.offer(nodeQ.left);
      } else if (nodeP.left != null || nodeQ.left != null) {
        // 一个有左孩子，一个没有，返回false
        return false;
      } // else 都没有左孩子，不处理

      if (nodeP.right != null && nodeQ.right != null) {
        queueP.offer(nodeP.right);
        queueQ.offer(nodeQ.right);
      } else if (nodeP.right != null || nodeQ.right != null) {
        // 一个有右孩子，一个没有，返回false
        return false;
      } // else 都没有右孩子，不处理
    }
    // 循环结束，如果两个队列都为空，ture，否则 false
    if(queueP.isEmpty() && queueQ.isEmpty()){
      return true;
    } else {
      return false;
    }
  }

}
