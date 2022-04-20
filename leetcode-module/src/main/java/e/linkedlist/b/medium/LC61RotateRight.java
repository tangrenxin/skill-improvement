package e.linkedlist.b.medium;

import e.linkedlist.ListNode;

/**
 * @Description:
 * 61. 旋转链表
 *
 * 给你一个链表的头节点 head ，旋转链表，将链表每个节点向右移动 k 个位置。
 *
 * 这个题，个人感觉跟 删除链表倒数第n个节点的解法很类似
 *
 * 例如：
 * 输入：head = [1,2,3,4,5], k = 2
 * 输出：[4,5,1,2,3]
 *
 * @Author: tangrenxin
 * @Date: 2022/3/3 20:24
 */
public class LC61RotateRight {

  public static void main(String[] args) {
    ListNode l0 = new ListNode(3, new ListNode(4, new ListNode(5)));
    ListNode l2 = new ListNode(1, new ListNode(2, l0));
    ListNode ll = l2;

    while (ll != null) {
      System.out.print(ll.val + "->");
      ll = ll.next;
    }

    System.out.println("\n操作后:");
    ListNode res = rotateRight(l2, 2);
    while (res != null) {
      System.out.print(res.val + "->");
      res = res.next;
    }
  }

  public static ListNode rotateRight(ListNode head, int k) {
    // 1.创建新head
    ListNode newHead;
    ListNode fastNode = head;
    ListNode lowNode = head;
    // 2.先将快指针移动k个节点
    while(k-- > 0){
      fastNode = fastNode.next;
    }
    // 3.将 fastNode 移动至最后一个节点后，lowNode 所在节点将是旋转后新链表的最后一个节点
    // lowNode 的 next 节点将是旋转后新链表的头结点
    while(fastNode.next != null){
      fastNode = fastNode.next;
      lowNode = lowNode.next;
    }
    // 4.新的头结点
    newHead = lowNode.next;
    // 5.断开连接
    lowNode.next = null;
    // 6.拼接到原来的头节点前
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
