package e.linkedlist.b.medium;

import e.linkedlist.ListNode;

/**
 * @Description:
 * 61. 旋转链表
 *
 * 给你一个链表的头节点 head ，旋转链表，将链表每个节点向右移动 k 个位置。
 *
 * 这个题，个人感觉跟 删除链表倒数第n个节点的解法很类似
 * @Author: tangrenxin
 * @Date: 2022/3/3 20:24
 */
public class LC61RotateRight {

  public static void main(String[] args) {
    rotateRight(null,2);
  }

  public static ListNode rotateRight(ListNode head, int k) {
    ListNode newHead;
    ListNode fastNode = head;
    ListNode lowNode = head;
    // 先将快指针移动k个节点
    while(k-- > 0){
      fastNode = fastNode.next;
    }
    // 将 fastNode 移动至最后一个节点后，lowNode所在节点将是旋转后新链表的最后一个节点
    // lowNode 的 next节点将是旋转后新链表的头结点
    while(fastNode.next != null){
      fastNode = fastNode.next;
      lowNode = lowNode.next;
    }
    newHead = lowNode.next;
    lowNode.next = null;
    fastNode.next = head;
    return newHead;
  }

  public static ListNode rotateRight2(ListNode head, int k) {
    if(head == null || head.next == null){
      return head;
    }
    int n = 1;
    ListNode iter = head;
    while (iter.next != null) {
      iter = iter.next;
      n++;
    }
    int add = n - k % n;
    if (add == n) {
      return head;
    }
    iter.next = head;
    while (add-- > 0) {
      iter = iter.next;
    }
    ListNode ret = iter.next;
    iter.next = null;
    return ret;
  }
}
