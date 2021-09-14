package e.linkedlist;

/**
 * @Description:
 * Node 类，value是int型
 * @Author: tangrenxin
 * @Date: 2021/9/12 21:55
 */
public class ListNode {

  public int val;
  public ListNode next;

  public ListNode(int data) {
    this.val = data;
    this.next = null;
  }

  public ListNode(int data, ListNode next) {
    this.val = data;
    this.next = next;
  }

}
