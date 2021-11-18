package e.linkedlist;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/11/6 23:40
 */
public class ListNodeUtils {

  public static ListNode getLinkedList(int[] nums) {
    ListNode head = null;
    ListNode moveHead = null;
    for (int num : nums) {
      ListNode listNode = new ListNode(num);
      if (head == null) {
        head = listNode;
        moveHead = head;
        continue;
      }
      moveHead.next = listNode;
      moveHead = moveHead.next;
    }
    return head;
  }

  public static void printLinkedList(ListNode head) {
    if (head == null) {
      return;
    }
    while (head != null) {
      if (head.next == null) {
        System.out.print(head.val);
      } else {
        System.out.print(head.val + "->");
      }
      head = head.next;
    }
  }

}
