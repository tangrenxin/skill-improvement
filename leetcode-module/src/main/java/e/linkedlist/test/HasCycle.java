package e.linkedlist.test;

import e.linkedlist.ListNode;
import e.linkedlist.ListNodeUtils;

/**
 * @Description:
 * 环形链表
 * @Author: tangrenxin
 * @Date: 2022/3/1 22:13
 */
public class HasCycle {

  public static void main(String[] args) {
    // 判断链表是否有环
    int[] nums = {3, 2, 0, -4};
    ListNode head = ListNodeUtils.getLinkedList(nums);
    System.out.println(hasCycle(head));

  }

  public static boolean hasCycle(ListNode head) {

    if (head == null || head.next == null || head.next.next == null) {
      return false;
    }
    ListNode lowPoint = head.next;
    ListNode fastPoint = head.next.next;
    while (fastPoint != null && fastPoint.next != null) {
      if (lowPoint == fastPoint) {
        return true;
      }
      lowPoint = lowPoint.next;
      fastPoint = fastPoint.next.next;
    }
    return false;
  }

}
