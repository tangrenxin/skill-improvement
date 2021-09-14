package e.linkedlist.a.easy;

import e.linkedlist.ListNode;

/**
 * @Description:
 *
【21. 合并两个有序链表】
两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。

示例 1：

输入：l1 = [1,2,4], l2 = [1,3,4]
输出：[1,1,2,3,4,4]

示例 2：

输入：l1 = [], l2 = []
输出：[]

示例 3：

输入：l1 = [], l2 = [0]
输出：[0]

提示：

两个链表的节点数目范围是 [0, 50]
-100 <= Node.val <= 100
l1 和 l2 均按 非递减顺序 排列

 * @Author: tangrenxin
 * @Date: 2021/9/6 17:20
 */
public class LeetCode21 {

  public static void main(String[] args) {

    // 生成链表：
    ListNode l1 = new ListNode(1, new ListNode(2, new ListNode(4)));
    ListNode l2 = new ListNode(1, new ListNode(3, new ListNode(4)));
    ListNode res = mergeTwoLists(l1, l2);
    while (res != null) {
      System.out.print(res.val + "->");
      res = res.next;
    }
  }

  /**
   * 非递归方式
   * @param l1
   * @param l2
   * @return
   */
  public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    // 定义头部节点，设置一个默认值 巧妙的避开空指针
    ListNode prehead = new ListNode(-1);
    // 移动指针
    ListNode prev = prehead;
    while (l1 != null && l2 != null) {
      if (l1.val <= l2.val) {
        prev.next = l1;
        l1 = l1.next;
      } else {
        prev.next = l2;
        l2 = l2.next;
      }
      prev = prev.next;
    }
    // 合并后 l1 和 l2 最多只有一个还未被合并完，我们直接将链表末尾指向未合并完的链表即可
    prev.next = l1 == null ? l2 : l1;

    return prehead.next;
  }

  /**
   * 递归方式
   * @param l1
   * @param l2
   * @return
   */
  public static ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
    if (l1 == null) {
      return l2;
    } else if (l2 == null) {
      return l1;
    } else if (l1.val < l2.val) {
      l1.next = mergeTwoLists(l1.next, l2);
      return l1;
    } else {
      l2.next = mergeTwoLists(l1, l2.next);
      return l2;
    }
  }
}
