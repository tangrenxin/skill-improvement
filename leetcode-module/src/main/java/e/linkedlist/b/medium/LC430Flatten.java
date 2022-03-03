package e.linkedlist.b.medium;

import e.linkedlist.Node;

/**
 * @Description:
 * 430. 扁平化多级双向链表
 *
 * 你会得到一个双链表，其中包含的节点有一个下一个指针、一个前一个指针和一个额外的 子指针 。这个子指针可能指向一个单独的双向链表，也包含这些特殊的节点。这些子列表可以有一个或多个自己的子列表，以此类推，以生成如下面的示例所示的 多层数据结构 。
 *
 * 给定链表的头节点 head ，将链表 扁平化 ，以便所有节点都出现在单层双链表中。让 curr 是一个带有子列表的节点。子列表中的节点应该出现在扁平化列表中的 curr 之后 和 curr.next 之前 。
 *
 * 返回 扁平列表的 head 。列表中的节点必须将其 所有 子指针设置为 null 。
 * @Author: tangrenxin
 * @Date: 2022/3/3 19:41
 */
public class LC430Flatten {

  public Node flatten(Node head) {
    // 定义移动指针
    Node currNode = head;
    // 当遇到节点的child ！= null 时，保存当前节点的next节点
    Node currNextNode;
    while (currNode != null) {
      if (currNode.child != null) {
        // 保存当前节点的next节点
        currNextNode = currNode.next;
        // 子指针的 prev 指向当前节点
        currNode.child.prev = currNode;
        // 当前节点的next 指向 子指针
        currNode.next = currNode.child;
        // 获取 子指针 的最后一个节点(即childEndNode)
        Node childEndNode = getChildEndNode(currNode.child);
        // 将子指针的最后一个节点(即childEndNode)的 next 指向当前节点的next节点（即currNextNode）
        childEndNode.next = currNextNode;
        // 如果 当前节点的next节点（即currNextNode）不为空，需要将其 prev 指向子指针的最后一个节点(即childEndNode)
        if(currNextNode!=null){
          currNextNode.prev = childEndNode;
        }
        // 该节点的 子指针 链表扁平化完成，置为 null
        currNode.child = null;
      }
      // 处理下一个节点
      currNode = currNode.next;
    }
    return head;
  }

  private Node getChildEndNode(Node head) {
    while (head.next != null) {
      head = head.next;
    }
    return head;
  }
}
