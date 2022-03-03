package e.linkedlist.b.medium;

import e.linkedlist.ListNode;

/**
 * @Description:
 * 【19. 删除链表的倒数第 N 个结点】
 * @Author: tangrenxin
 * @Date: 2022/3/1 23:47
 */
public class LC19RemoveNthFromEnd {


  /**
   * 双指针法
   * 由于我们需要找到倒数第 n 个节点，因此我们可以使用两个指针 first 和 second 同时对链表进行遍历，
   * 并且first 比second 超前 n 个节点。
   * 当 first 遍历到链表的末尾时，second 就恰好处于倒数第 n 个节点。
   * @param head
   * @param n
   * @return
   */
  public ListNode removeNthFromEnd(ListNode head, int n) {
    // 创建一个哑结点，使得first移动到末尾时，second正好在倒数第n个节点的前一个
    ListNode dummy = new ListNode(0,head);
    ListNode first = head;
    ListNode second = dummy;
    // 先将first移动 n 个节点，使得first 比 second节点超前n个
    for (int i = 0; i < n; i++) {
      first = first.next;
    }
    // first second 两个指针一起移动，当first 到达末尾时，second指针指向 倒数第n个节点的前一个
    while(first != null){
      first = first.next;
      second = second.next;
    }

    // 删除节点
    second.next = second.next.next;
    return dummy.next;
  }

}
