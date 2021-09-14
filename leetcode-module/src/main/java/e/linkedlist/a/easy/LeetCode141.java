package e.linkedlist.a.easy;

import e.linkedlist.ListNode;

/**
 * @Description:
 * 【141. 环形链表】 判断链表是否有环
 *给定一个链表，判断链表中是否有环。
 *
 * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。
 *
 * 如果链表中存在环，则返回 true 。 否则，返回 false 。
 *
 *
 *
 * 进阶：
 *
 * 你能用 O(1)（即，常量）内存解决此问题吗？
 *
 *
 *
 * 示例 1：
 *
 * 输入：head = [3,2,0,-4], pos = 1
 * 输出：true
 * 解释：链表中有一个环，其尾部连接到第二个节点。
 *
 * 示例 2：
 *
 * 输入：head = [1,2], pos = 0
 * 输出：true
 * 解释：链表中有一个环，其尾部连接到第一个节点。
 *
 * 示例 3：
 *
 * 输入：head = [1], pos = -1
 * 输出：false
 * 解释：链表中没有环。
 *
 *
 *
 * 提示：
 *
 *     链表中节点的数目范围是 [0, 104]
 *     -105 <= Node.val <= 105
 *     pos 为 -1 或者链表中的一个 有效索引 。
 * @Author: tangrenxin
 * @Date: 2021/9/12 22:52
 */
public class LeetCode141 {

  public static void main(String[] args) {
    // 生成链表：
    ListNode l0 = new ListNode(1);
    ListNode l1 = new ListNode(1, new ListNode(1, new ListNode(2, l0)));
    l0.next = l1.next;

    System.out.println(hasCycle(l1));
  }

  /**
   * 分别定义两个移动指针，
   * a每次移动一步
   * b每次移动2步
   * 如果ab重合，说明有环
   * @param head
   * @return
   */
  public static boolean hasCycle(ListNode head) {
    ListNode headA = head;
    ListNode headB = head;
    while (headB != null && headB.next != null) {
      headA = headA.next;
      headB = headB.next.next;
      if (headA == headB) {
        return true;
      }
    }
    return false;
  }

}
