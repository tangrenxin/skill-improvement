package e.linkedlist.test;

import e.linkedlist.ListNode;
import e.linkedlist.ListNodeUtils;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description:
 * 环形链表2
 * 返回环形节点
 * @Author: tangrenxin
 * @Date: 2022/3/1 22:13
 */
public class HasCycle2 {

  public static void main(String[] args) {
    // 判断链表是否有环
    int[] nums = {3, 2, 0, -4};
    ListNode head = ListNodeUtils.getLinkedList(nums);
    System.out.println(detectCycle(head));

  }

  /**
   * 方法一：哈希表
   * @param head
   * @return
   */
  public static ListNode detectCycle(ListNode head) {
    ListNode pos = head;
    Set<ListNode> visited = new HashSet<ListNode>();
    while (pos != null) {
      if (visited.contains(pos)) {
        return pos;
      } else {
        visited.add(pos);
      }
      pos = pos.next;
    }
    return null;
  }

  /**
   * 方法二：快慢指针
   * 官方算法
   *
   * 我们使用两个指针，fast\textit{fast}fast 与 slow。它们起始都位于链表的头部。随后，slow 指针每次向后移动一个位置，而 fast\textit{fast}fast 指针向后移动两个位置。如果链表中存在环，则 fast\textit{fast}fast 指针最终将再次与 slow 指针在环中相遇。
   *
   * 如下图所示，设链表中环外部分的长度为 aaa。slow 指针进入环后，又走了 bbb 的距离与 fast\textit{fast}fast 相遇。此时，fast\textit{fast}fast 指针已经走完了环的 nnn 圈，因此它走过的总距离为 a+n(b+c)+b=a+(n+1)b+nca+n(b+c)+b=a+(n+1)b+nca+n(b+c)+b=a+(n+1)b+nc。
   *
   * fig1
   *
   * 根据题意，任意时刻，fast\textit{fast}fast 指针走过的距离都为 slow 指针的 222 倍。因此，我们有
   *
   * a+(n+1)b+nc=2(a+b)⟹a=c+(n−1)(b+c)a+(n+1)b+nc=2(a+b) \ a=c+(n-1)(b+c) a+(n+1)b+nc=2(a+b)⟹a=c+(n−1)(b+c)
   *
   * 有了 a=c+(n−1)(b+c)a=c+(n-1)(b+c)a=c+(n−1)(b+c) 的等量关系，我们会发现：从相遇点到入环点的距离加上 n−1n-1n−1 圈的环长，恰好等于从链表头部到入环点的距离。
   *
   * 因此，当发现 slow 与 fast\textit{fast}fast 相遇时，我们再额外使用一个指针 ptr\textit{ptr}ptr。起始，它指向链表头部；随后，它和 slow 每次向后移动一个位置。最终，它们会在入环点相遇。
   
   * @param head
   * @return
   */
  public static ListNode detectCycle2(ListNode head) {
    if (head == null) {
      return null;
    }
    ListNode slow = head, fast = head;
    while (fast != null) {
      slow = slow.next;
      if (fast.next != null) {
        fast = fast.next.next;
      } else {
        return null;
      }
      if (fast == slow) {
        ListNode ptr = head;
        while (ptr != slow) {
          ptr = ptr.next;
          slow = slow.next;
        }
        return ptr;
      }
    }
    return null;
  }

}
