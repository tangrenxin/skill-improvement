package e.linkedlist.c.difficulty;

import e.linkedlist.ListNode;
import e.linkedlist.ListNodeUtils;
import java.util.Stack;

/**
 * @Description:
 * 25. K 个一组翻转链表
 *
 * 给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
 * k 是一个正整数，它的值小于或等于链表的长度。
 * 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 *
 * 进阶：
 *
 *     你可以设计一个只使用常数额外空间的算法来解决此问题吗？
 *     你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
 * @Author: tangrenxin
 * @Date: 2021/11/7 01:14
 */
public class LC25ReverseKGroup {

  public static void main(String[] args) {
    int[] nums = {0, 1, 2, 3, 4, 5, 6, 7};
//    int[] nums = {1, 2, 3};
    ListNode head = ListNodeUtils.getLinkedList(nums);
    System.out.print("初始链表：");
    ListNodeUtils.printLinkedList(head);

    // 使用栈：
    ListNode headRes = reverseKgroup(head, 3);
    System.out.print("\n翻转链表：");
    ListNodeUtils.printLinkedList(headRes);
  }

  /**
   * 利用栈实现
   * @param head
   * @param k
   * @return
   */
  private static ListNode reverseKgroup(ListNode head, int k) {
    Stack<ListNode> stack = new Stack<>();
    // 翻转后的新表头
    ListNode resHead = null;
    // 新表最后一个节点
    ListNode resTail = null;
    // 节点计数
    int count = 0;
    // 保存遍历的当前节点的下一个节点
    ListNode currNext;
    while (head != null) {
      currNext = head.next;
      stack.push(head);
      count++;
      // 达到指定翻转数量
      if (count == k) {
        // 执行翻转
        while (!stack.isEmpty()) {
          ListNode node = stack.pop();
          if (resHead == null) {
            resHead = node;
            resTail = resHead;
            continue;
          }
          resTail.next = node;
          resTail = resTail.next;
          // 翻转后 断开原来的 连接 TODO（发现一个问题，链表的翻转类的程序，翻转后有很多需要断开原来的连接*** 要注意）
          node.next = null;
        }
        // 计数清零
        count = 0;
      }
      // 注意：如果这里写成 head = head.next，将进入死循环
      head = currNext;
    }
    // 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序：
    // 其实就是将 resTail.next 指向栈底的节点就行。
    while (!stack.isEmpty()) {
      ListNode node = stack.pop();
      // 如果栈为空，说明 node 就是栈底节点
      if (stack.isEmpty()) {
        if (resHead == null) {
          resHead = node;
        } else {
          resTail.next = node;
        }
      }
    }
    return resHead;
  }

}
