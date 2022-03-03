package e.linkedlist.b.medium;

import e.linkedlist.ListNode;
import e.linkedlist.ListNodeUtils;

/**
 * @Description:
 * 328. 奇偶链表
 *
 * 给定单链表的头节点 head ，将所有索引为奇数的节点和索引为偶数的节点分别组合在一起，然后返回重新排序的列表。
 *
 * 第一个节点的索引被认为是 奇数 ， 第二个节点的索引为 偶数 ，以此类推。
 *
 * 请注意，偶数组和奇数组内部的相对顺序应该与输入时保持一致。
 *
 * 你必须在 O(1) 的额外空间复杂度和 O(n) 的时间复杂度下解决这个问题。
 * @Author: tangrenxin
 * @Date: 2022/3/2 22:08
 */
public class LC328OddEvenList {

  public static void main(String[] args) {
    int[] nums = {1, 2, 3, 4, 5, 6};
    ListNode head = ListNodeUtils.getLinkedList(nums);
    System.out.print("初始链表：");
    ListNodeUtils.printLinkedList(head);
    System.out.println();

    // 遍历法
    ListNode headRes = oddEvenList(head);
    System.out.print("\n后链表：");
    ListNodeUtils.printLinkedList(headRes);
  }


  /**
   * 分离节点后 合并
   * 自己 实现
   * @param head
   * @return
   */
  public static ListNode oddEvenList(ListNode head) {

    // 分流思想，将奇偶数位的节点分开，分别建一个哑结点作为 奇 偶 节点的头结点
    // 遍历 head ，判断奇偶，将节点追加到对应的链表末尾
    // 最后再将 奇数链表的末尾 指向偶数链表的头结点，
    ListNode oddHead = new ListNode(-1, null);
    ListNode evenHead = new ListNode(-1, null);
    // 奇偶 链表的移动指针
    ListNode oddPoint = null;
    ListNode evenPoint = null;
    int i = 1;
    while (head != null) {
      if (i % 2 == 1) {
        // 奇数位
        if (oddPoint == null) {
          oddPoint = head;
          oddHead.next = oddPoint;
        } else {
          oddPoint.next = head;
          oddPoint = oddPoint.next;
        }
      } else {
        // 偶数位
        if (evenPoint == null) {
          evenPoint = head;
          evenHead.next = evenPoint;
        } else {
          evenPoint.next = head;
          evenPoint = evenPoint.next;
        }
      }
      i++;
      head = head.next;
    }
    // 注意，偶数链表的最后一个节点在原链表中不一定是最后一个，需要将其next指向 null
    // 如果不做这个操作，偶数链表的最后一个节点 可能会指向 奇数链表的最后一个节点，出现循环链表的情况
    if (evenPoint != null) {
      evenPoint.next = null;
    }
    // 奇数链表的末尾 指向偶数链表的头结点
    if (oddPoint != null) {
      oddPoint.next = evenHead.next;
    }

    return oddHead.next;
  }

  /**
   * 分离节点后 合并
   * 官方 实现
   * @param head
   * @return
   */
  public static ListNode oddEvenList2(ListNode head) {

    if (head == null) {
      return head;
    }

    // 分流思想，将奇偶数位的节点分开，分别建一个哑结点作为 奇 偶 节点的头结点
    ListNode evenHead = head.next;
    // 奇偶 链表的移动指针
    ListNode oddPoint = head, evenPoint = evenHead;
    while (evenPoint != null && evenPoint.next != null) {
      oddPoint.next = evenPoint.next;
      oddPoint = oddPoint.next;
      evenPoint.next = oddPoint.next;
      evenPoint = evenPoint.next;
    }
    return head;
  }

}
