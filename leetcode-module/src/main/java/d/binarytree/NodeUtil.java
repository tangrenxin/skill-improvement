package d.binarytree;

import java.util.LinkedList;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/10/26 20:37
 */
public class NodeUtil {

  TreeNode root;
  String[] partTree;

  public static TreeNode createBinTree(int[] array) {
    LinkedList list = new LinkedList<TreeNode>();
    // 将一个数组的值依次转换为TreeNode节点  
    for (int treeNodeIndex = 0; treeNodeIndex < array.length; treeNodeIndex++) {
      list.add(new TreeNode(array[treeNodeIndex]));
    }
    TreeNode rootNode = (TreeNode) list.get(0);
    // 对前lastParentIndex-1个父节点按照父节点与孩子节点的数字关系建立二叉树
    for (int parentIndex = 0; parentIndex < array.length / 2 - 1; parentIndex++) {
      // 左孩子  
      ((TreeNode) list.get(parentIndex)).left = (TreeNode) list.get(parentIndex * 2 + 1);
      // 右孩子  
      ((TreeNode) list.get(parentIndex)).right = (TreeNode) list.get(parentIndex * 2 + 2);
    }
    // 最后一个父节点:因为最后一个父节点可能没有右孩子，所以单独拿出来处理  
    int lastParentIndex = array.length / 2 - 1;
    // 左孩子  
    ((TreeNode) list.get(lastParentIndex)).left = (TreeNode) list.get(lastParentIndex * 2 + 1);
    // 右孩子,如果数组的长度为奇数才建立右孩子  
    if (array.length % 2 == 1) {
      ((TreeNode) list.get(lastParentIndex)).right = (TreeNode) list.get(lastParentIndex * 2 + 2);
    }
    return rootNode;
  }

  public String[] intialInput(String s) {
    String s1 = s.substring(1, s.length() - 1);
    partTree = s1.split(",");
    return partTree;
  }

  public TreeNode createNode(TreeNode rot, int index) {
    if (index >= partTree.length) {            //从而root实际指向位置不变，所以返回值类型为TreeNode
      return null;
    }
    if (partTree[index].equals("null")) {          //equals判断，而不是==
      return null;
    }
    rot = new TreeNode(Integer.parseInt(partTree[index]));
    rot.left = createNode(rot.left, 2 * index + 1);
    rot.right = createNode(rot.right, 2 * index + 2);
    return rot;
  }

  public TreeNode createTree(String s) {
    partTree = intialInput(s);
    root = createNode(root, 0);
    return root;
  }

  public static void main (String[] args) {
    NodeUtil tt=new NodeUtil();
    String treeExp="[5,4,8,11,null,13,4,7,2,null,null,null,1]";
    tt.createTree(treeExp);

  }

}
