package d.binarytree;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/9/14 01:19
 */
public class TreeNode {

  int val;
  TreeNode left;
  TreeNode right;

  TreeNode() {
  }

  TreeNode(int val) {
    this.val = val;
  }

  TreeNode(int val, TreeNode left, TreeNode right) {
    this.val = val;
    this.left = left;
    this.right = right;
  }
}
