package f.stack.a.easy;

import java.util.Stack;

/**
 * @Description:
 * 【155. 最小栈】
 * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
 *
 *     push(x) —— 将元素 x 推入栈中。
 *     pop() —— 删除栈顶的元素。
 *     top() —— 获取栈顶元素。
 *     getMin() —— 检索栈中的最小元素。
 *
 *
 *
 * 示例:
 *
 * 输入：
 * ["MinStack","push","push","push","getMin","pop","top","getMin"]
 * [[],[-2],[0],[-3],[],[],[],[]]
 *
 * 输出：
 * [null,null,null,null,-3,null,0,-2]
 *
 * 解释：
 * MinStack minStack = new MinStack();
 * minStack.push(-2);
 * minStack.push(0);
 * minStack.push(-3);
 * minStack.getMin();   --> 返回 -3.
 * minStack.pop();
 * minStack.top();      --> 返回 0.
 * minStack.getMin();   --> 返回 -2.
 * @Author: tangrenxin
 * @Date: 2021/9/13 02:17
 */
public class LeetCode155_MinStack {

  public static void main(String[] args) throws Exception {
    MinStack minStack = new MinStack();

    minStack.push(4);
    minStack.push(2);
    minStack.push(3);
    minStack.push(2);
    minStack.push(1);
    minStack.push(8);
    System.out.println(minStack.getMin());
    minStack.pop();
    System.out.println(minStack.getMin());
    minStack.pop();
    System.out.println(minStack.getMin());
    minStack.pop();
    System.out.println(minStack.getMin());

  }

}

/**
 * 创建一个辅助栈，用于存放历史的最小值：
 * 没push一个值，就与minStack的最小值作比较，如果val比较小，入minStack栈
 */
class MinStack {

  private Stack<Integer> mainStack = new Stack<>();
  private Stack<Integer> minStack = new Stack<>();

  public MinStack() {

  }

  public void push(int val) {
    if (mainStack.isEmpty()) {
      minStack.push(val);
    } else {
      if (minStack.peek() >= val) {
        minStack.push(val);
      }
    }
    mainStack.push(val);
  }

  public Integer pop() {
    if (!minStack.isEmpty() && minStack.peek().equals(mainStack.peek())) {
      minStack.pop();
    }
    return mainStack.pop();
  }

  public int top() {
    return mainStack.peek();
  }

  public int getMin() throws Exception {
    if (minStack.isEmpty()) {
      throw new Exception("Stack id empty");
    }
    return minStack.peek();
  }
}
