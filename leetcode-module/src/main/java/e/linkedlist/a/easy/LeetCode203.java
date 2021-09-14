package e.linkedlist.a.easy;

import e.linkedlist.ListNode;

/**
 * @Description:
 * 【203. 移除链表元素】
 *给你一个链表的头节点 head 和一个整数 val ，请你删除链表中所有满足 Node.val == val 的节点，并返回 新的头节点 。
 *
 *
 *
 * 示例 1：
 *
 * 输入：head = [1,2,6,3,4,5,6], val = 6
 * 输出：[1,2,3,4,5]
 *
 * 示例 2：
 *
 * 输入：head = [], val = 1
 * 输出：[]
 *
 * 示例 3：
 *
 * 输入：head = [7,7,7,7], val = 7
 * 输出：[]
 *
 *
 *
 * 提示：
 *
 *     列表中的节点数目在范围 [0, 104] 内
 *     1 <= Node.val <= 50
 *     0 <= val <= 50

 * @Author: tangrenxin
 * @Date: 2021/9/12 23:49
 */
public class LeetCode203 {

  public static void main(String[] args) {
    // 生成链表：
    ListNode l0 = new ListNode(1, new ListNode(1, new ListNode(2)));
    ListNode l1 = new ListNode(2, new ListNode(3, new ListNode(4, l0)));
    ListNode l2 = new ListNode(2, new ListNode(3, l0));

    ListNode res = removeElements(l1, 2);
    while (res != null) {
      System.out.print(res.val + "->");
      res = res.next;
    }
  }

  /**
   * 遍历 删除节点
   * @param head
   * @param val
   * @return
   */
  public static ListNode removeElements(ListNode head, int val) {
    // 移动指针
    ListNode headPoint = new ListNode(-1);
    headPoint.next = head;
    // 结果链表头
    ListNode headRes = headPoint;
    while (headPoint != null && headPoint.next != null) {
      if (headPoint.next.val == val) {
        headPoint.next = headPoint.next.next;
        continue;
      }
      headPoint = headPoint.next;
    }
    return headRes.next;
  }
}
