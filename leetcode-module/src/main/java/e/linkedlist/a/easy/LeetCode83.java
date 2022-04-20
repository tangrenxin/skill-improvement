package e.linkedlist.a.easy;

import e.linkedlist.ListNode;

/**
 * @Description:
 * 【83. 删除排序链表中的重复元素】
 * 存在一个按升序排列的链表，给你这个链表的头节点 head ，请你删除所有重复的元素，使每个元素 只出现一次 。
 *
 * 返回同样按升序排列的结果链表。
 *
 * 示例 1：
 *
 * 输入：head = [1,1,2]
 * 输出：[1,2]
 *
 * 示例 2：
 *
 * 输入：head = [1,1,2,3,3]
 * 输出：[1,2,3]
 *
 * 提示：
 *
 *     链表中节点数目在范围 [0, 300] 内
 *     -100 <= Node.val <= 100
 *     题目数据保证链表已经按升序排列
 * @Author: tangrenxin
 * @Date: 2021/9/12 22:52
 */
public class LeetCode83 {

  public static void main(String[] args) {
    // 生成链表：
    ListNode l1 = new ListNode(1,
        new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(3)))));
    ListNode l2 = new ListNode(1, new ListNode(3, new ListNode(4)));
    ListNode res = deleteDuplicates(l1);
    while (res != null) {
      System.out.print(res.val + "->");
      res = res.next;
    }
  }

  /**
   * 数据保证链表已经按升序排列
   * 如果下一节点的value - 当前节点的value == 0 说明节点重复
   * @param head
   * @return
   */
  public static ListNode deleteDuplicates(ListNode head) {
    // 移动指针
    ListNode prev = head;
    while (prev != null && prev.next != null) {
      if (prev.val == prev.next.val) {
        prev.next = prev.next.next;
      } else {
        prev = prev.next;
      }
    }
    return head;
  }

  public static ListNode deleteDuplicates2(ListNode head) {
    ListNode prev = head;
    while (prev != null && prev.next != null) {
      if (prev.val == prev.next.val) {
        prev.next = prev.next.next;
      } else {
        prev = prev.next;
      }
    }
    return head;
  }


}
