package d.binarytree;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/11/20 11:04
 */
public class TreeNodeUtils {

  public static void main(String[] args) {
    String str = "[5,4,8,11,null,13,4,7,2,null,null,null,1]";
    TreeNode root = stringToTree(str);
    prevOrder(root);
  }

  //  先序遍历
  public static void prevOrder(TreeNode root) {
    if (root == null) {
      return;
    }
    System.out.print(root.val + ",");
    prevOrder(root.left);
    prevOrder(root.right);
  }

  public static TreeNode stringToTree(String str) {
    String[] elems = stringToArray(str);
    if (null == elems) {
      return null;
    }

    TreeNode root = createTree(elems, 0);
    return root;
  }

  /**
   * 递归构建二叉树
   *
   * @param arr
   * @param index
   * @return
   */
  private static TreeNode createTree(String[] arr, int index) {
    TreeNode tn = null;
    if (index < arr.length) {
      if (arr[index].equals("null")) {
        return null;
      }
      Integer val = Integer.valueOf(arr[index]);
      tn = new TreeNode(val);
      // 构建二叉树左子树
      tn.left = createTree(arr, 2 * index + 1);
      // 构建二叉树右子树
      tn.right = createTree(arr, 2 * index + 2);
    }
    return tn;
  }

  private static String[] stringToArray(String str) {
    if (null != str && str.equals("")) {
      return null;
    }
    String[] fields = str.split(",");
    fields[0] = fields[0].substring(1);
    int lastIndex = fields.length - 1;
    fields[lastIndex] = fields[lastIndex].substring(0, 1);
    return fields;
  }

  public void preOrder(TreeNode root) {
    if (root == null) {
      return;
    }
    System.out.println(root.val);
    preOrder(root.left);
    preOrder(root.right);
  }

}
