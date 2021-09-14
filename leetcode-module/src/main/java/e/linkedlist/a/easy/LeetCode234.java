package e.linkedlist.a.easy;

import e.linkedlist.ListNode;
import java.util.Stack;

/**
 * @Description:
 * 【234. 回文链表】
 * 给你一个单链表的头节点 head ，请你判断该链表是否为回文链表。如果是，返回 true ；否则，返回 false 。
 *
 *
 *
 * 示例 1：
 *
 * 输入：head = [1,2,2,1]
 * 输出：true
 *
 * 示例 2：
 *
 * 输入：head = [1,2]
 * 输出：false
 *
 *
 *
 * 提示：
 *
 *     链表中节点数目在范围[1, 105] 内
 *     0 <= Node.val <= 9
 *
 *
 * 进阶：你能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？
 *
 * @Author: tangrenxin
 * @Date: 2021/9/12 23:49
 */
public class LeetCode234 {

  public static void main(String[] args) {
    // 生成链表：
    ListNode l0 = new ListNode(4, new ListNode(3, new ListNode(2)));
    ListNode l1 = new ListNode(2, new ListNode(3, new ListNode(4, l0)));
    ListNode l2 = new ListNode(2, new ListNode(3, l0));

    System.out.println(isPalindrome(l2));
    System.out.println(isPalindrome2(l2));

  }

  /**
   * 使用栈的方式
   * 时间复杂度O(n) 空间复杂度O(n)
   * @param head
   * @return
   */
  public static boolean isPalindrome(ListNode head) {
    Stack<ListNode> stack = new Stack<>();
    ListNode node = head;
    while (node != null) {
      stack.push(node);
      node = node.next;
    }
    while (head != null) {
      if (head.val != stack.pop().val) {
        return false;
      } else {
        head = head.next;
      }
    }
    return true;
  }

  /**
   * 快慢指针
   *
   * 思路
   *
   * 避免使用 O(n)O(n)O(n) 额外空间的方法就是改变输入。
   *
   * 我们可以将链表的后半部分反转（修改链表结构），然后将前半部分和后半部分进行比较。比较完成后我们应该将链表恢复原样。
   * 虽然不需要恢复也能通过测试用例，但是使用该函数的人通常不希望链表结构被更改。
   *
   * 该方法虽然可以将空间复杂度降到 O(1)O(1)O(1)，但是在并发环境下，该方法也有缺点。在并发环境下，函数运行时需要锁定
   * 其他线程或进程对链表的访问，因为在函数执行过程中链表会被修改。
   *
   * 算法
   *
   * 整个流程可以分为以下五个步骤：
   *
   *     找到前半部分链表的尾节点。
   *     反转后半部分链表。
   *     判断是否回文。
   *     恢复链表。
   *     返回结果。
   * @param head
   * @return
   */
  public static boolean isPalindrome2(ListNode head) {
    if (head == null) {
      return true;
    }

    // 找到前半部分链表的尾节点并反转后半部分链表
    ListNode firstHalfEnd = endOfFirstHalf(head);
    ListNode secondHalfStart = reverseList(firstHalfEnd.next);

    // 判断是否回文
    ListNode p1 = head;
    ListNode p2 = secondHalfStart;
    boolean result = true;
    while (result && p2 != null) {
      if (p1.val != p2.val) {
        result = false;
      }
      p1 = p1.next;
      p2 = p2.next;
    }

    // 还原链表并返回结果
    firstHalfEnd.next = reverseList(secondHalfStart);
    return result;
  }

  private static ListNode reverseList(ListNode head) {
    ListNode prev = null;
    ListNode curr = head;
    while (curr != null) {
      ListNode nextTemp = curr.next;
      curr.next = prev;
      prev = curr;
      curr = nextTemp;
    }
    return prev;
  }

  private static ListNode endOfFirstHalf(ListNode head) {
    ListNode fast = head;
    ListNode slow = head;
    while (fast.next != null && fast.next.next != null) {
      fast = fast.next.next;
      slow = slow.next;
    }
    return slow;
  }

}
