package e.linkedlist.a.easy;

import e.linkedlist.ListNode;
import e.linkedlist.ListNodeUtils;

/**
 * @Description:
 * 【206. 反转链表】
 * 给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。
 *
 * 示例 1：
 * 输入：head = [1,2,3,4,5]
 * 输出：[5,4,3,2,1]
 * 示例 2：
 * 输入：head = [1,2]
 * 输出：[2,1]
 * 示例 3：
 * 输入：head = []
 * 输出：[]
 * 提示：
 *     链表中节点的数目范围是 [0, 5000]
 *     -5000 <= Node.val <= 5000
 * 进阶：链表可以选用迭代或递归方式完成反转。你能否用两种方法解决这道题？
 * @Author: tangrenxin
 * @Date: 2021/11/6 23:40
 */
public class LC206ReverseList {

  public static void main(String[] args) {
    int[] nums = {0, 1, 2, 3, 4, 5, 6};
    ListNode head = ListNodeUtils.getLinkedList(nums);
    System.out.print("初始链表：");
    ListNodeUtils.printLinkedList(head);

    // 遍历法
    ListNode headRes = reverseListMethod1(head);
    System.out.print("\n翻转链表：");
    ListNodeUtils.printLinkedList(headRes);

    head = ListNodeUtils.getLinkedList(nums);
    // 递归法
    ListNode headRes2 = reverseListMethod2(head);
    System.out.print("\n翻转链表：");
    ListNodeUtils.printLinkedList(headRes2);

    ListNode headRes3 = reverseListMethod1Test(head);
    System.out.print("\n reverseListMethod1Test翻转链表：");
    ListNodeUtils.printLinkedList(headRes3);
  }

  /**
   * 递归法：
   * 总体来说，递归法是从最后一个Node开始，在弹栈的过程中将指针顺序置换的。
   * 1.什么时候回归？ head.next == null ?
   * @param head
   * @return
   */
  private static ListNode reverseListMethod2(ListNode head) {
    if (head == null || head.next == null) {
      // head == null 这个条件 是为了防止 输入的第一个head为空的情况？
      // head.next == null 表示当前节点是链表的最后一个节点，需要返回。
      // 这个地方返回的 head 就是翻转后新链表的表头，相当于 遍历法 里面的 resHead
      return head;
    }
    // 1.当前节点的 next 节点，后续 currHeadNext 需要的next返回来指向 head，而 head 需要断开与原来的连接，指向 null
    ListNode currHeadNext = head.next;
    // 2.递归获取 新链表的表头（第一个表头就是旧链表的最后一个节点，也是递归的return条件）
    ListNode resHead = reverseListMethod2(head.next);
    // 3.执行翻转：原来是 head->currHeadNext,翻转后：currHeadNext->head->null
    currHeadNext.next = head;
    // 4.head 需要断开与原来的连接:head->null
    head.next = null;
    return resHead;
  }


  /**
   * 遍历法实现链表翻转
   * 遍历法就是在链表遍历的过程中将指针顺序置换
   * @param head
   * @return
   */
  private static ListNode reverseListMethod1(ListNode head) {
    // 翻转后的结果表头
    ListNode resHead = null;
    // 保存当前节点的 next 指向的节点
    ListNode currHeadNext;
    while (head != null) {
      // 1.先保存 head 的 next 节点，保证数据不丢失
      currHeadNext = head.next;
      // 2.将当前节点 head 插到结果表头的前面，此时 head 变成了结果表的表头
      head.next = resHead;
      // 3.将结果表的表头 resHead 指向 head
      resHead = head;
      // 4.此时因为 head 作为循环的参数，需要移动至原来的 next 节点，也就是第一步保存的 currHeadNext
      head = currHeadNext;
    }
    return resHead;
  }


  private static ListNode reverseListMethod1Test(ListNode head) {

    ListNode newHead = null;
    // 当前节点的下一个节点
    ListNode currNext;
    while (head!=null){
      currNext = head.next;
      head.next = newHead;
      newHead = head;
      head=currNext;
    }
    return newHead;
  }

}
