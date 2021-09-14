package e.linkedlist.a.easy;

import e.linkedlist.ListNode;

/**
 * @Description:
 * 【206. 反转链表】
 *给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。
 *
 *
 *
 * 示例 1：
 *
 * 输入：head = [1,2,3,4,5]
 * 输出：[5,4,3,2,1]
 *
 * 示例 2：
 *
 * 输入：head = [1,2]
 * 输出：[2,1]
 *
 * 示例 3：
 *
 * 输入：head = []
 * 输出：[]
 *
 *
 *
 * 提示：
 *
 *     链表中节点的数目范围是 [0, 5000]
 *     -5000 <= Node.val <= 5000
 *
 *
 *
 * 进阶：链表可以选用迭代或递归方式完成反转。你能否用两种方法解决这道题？
 * @Author: tangrenxin
 * @Date: 2021/9/12 23:49
 */
public class LeetCode206 {

  public static void main(String[] args) {
    // 生成链表：
    ListNode l0 = new ListNode(1, new ListNode(1, new ListNode(2)));
    ListNode l1 = new ListNode(2, new ListNode(3, new ListNode(4, l0)));
    ListNode l2 = new ListNode(2, new ListNode(3, l0));

    ListNode res = reverseList(l1);
    while (res != null) {
      System.out.print(res.val + "->");
      res = res.next;
    }
  }

  /**
   * 迭代的方法
   * @param head
   * @return
   */
  public static ListNode reverseList(ListNode head) {
    // 新的头结点
    ListNode pre = null;
    // 用于保存当前节点的下一个节点
    ListNode next;

    while (head != null) {
      // 获取保存当前节点的下一个节点
      next = head.next;
      // 交换当前节点与 头结点的关系
      head.next = pre;
      pre = head;
      head = next;
    }
    return pre;
  }


  /**
   * 递归的方法 (不是很懂)
   * @param head
   * @return
   */
  public static ListNode reverse(ListNode head) {
    if (head == null || head.next == null) {
      return head;
    }
    // 保存当前节点的下一个节点
    ListNode tmp = head.next;
    // 当前节点的新的头节点
    ListNode newHead = reverse(head.next);
    // 当前节点的下一个节点 反过来指向当前节点
    tmp.next = head;
    head.next = null;
    return newHead;
  }
}
