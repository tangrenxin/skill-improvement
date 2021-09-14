package d.binarytree;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/9/14 01:21
 */
public class Test {

  public static void main(String[] args) {

  }

  //  先序遍历
  public void prevOrder(TreeNode root) {
    if (root == null) {
      return;
    }
    System.out.println(root.val + ",");
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
}
