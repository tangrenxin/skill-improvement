package f.stack.a.easy;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @Description:
 * 496. 下一个更大元素 I
 * 给你两个 没有重复元素 的数组 nums1 和 nums2 ，其中nums1 是 nums2 的子集。
 * 请你找出 nums1 中每个元素在 nums2 中的下一个比其大的值。
 * nums1 中数字 x 的下一个更大元素是指 x 在 nums2 中对应位置的右边的第一个比 x 大的元素。如果不存在，对应位置输出 -1 。
 * 示例 1:
 * 输入: nums1 = [4,1,2], nums2 = [1,3,4,2].
 * 输出: [-1,3,-1]
 * 解释:
 *     对于 num1 中的数字 4 ，你无法在第二个数组中找到下一个更大的数字，因此输出 -1 。
 *     对于 num1 中的数字 1 ，第二个数组中数字1右边的下一个较大数字是 3 。
 *     对于 num1 中的数字 2 ，第二个数组中没有下一个更大的数字，因此输出 -1 。
 *
 * 示例 2:
 * 输入: nums1 = [2,4], nums2 = [1,2,3,4].
 * 输出: [3,-1]
 * 解释:
 *     对于 num1 中的数字 2 ，第二个数组中的下一个较大数字是 3 。
 *     对于 num1 中的数字 4 ，第二个数组中没有下一个更大的数字，因此输出 -1 。
 *
 * 提示：
 *     1 <= nums1.length <= nums2.length <= 1000
 *     0 <= nums1[i], nums2[i] <= 104
 *     nums1和nums2中所有整数 互不相同
 *     nums1 中的所有整数同样出现在 nums2 中
 * 进阶：你可以设计一个时间复杂度为 O(nums1.length + nums2.length) 的解决方案吗？
 *
 * @Author: tangrenxin
 * @Date: 2021/10/26 21:43
 */
public class LC496NextGreaterElement {

  public static void main(String[] args) {
    int[] num1 = {4,1,2};
    int[] num2 = {1,5,6,4,3,2,7};
    int[] res = nextGreaterElement(num1, num2);
    for (int re : res) {
      System.out.print(re+",");
    }
  }

  /**
   * 用栈实现：
   * 分别计算出 nums2 中前 n-1 个元素在 nums2 中下一个较大值，存到map中，得到<元素,该元素下一个较大值>
   * 遍历 nums1 ，从map中取数，如果get=null,填充-1
   * @param nums1
   * @param nums2
   * @return
   */
  public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
    // nums1 = [4,1,2], nums2 = [1,3,4,2,1,5].
    int[] list = new int[nums1.length];
    Stack<Integer> stack = new Stack<>();
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums2.length; i++) {
      // 1.如果 栈顶元素 < 当前元素，说明栈顶元素的下一个较大值就是当前元素，弹出栈顶加入map，
      //   弹出后继续比较当前值与当前栈顶的大小关系，直到栈中没有元素为止
      // 2.如果 栈顶元素 > 当前元素，说明当前值不是栈顶元素要找的下一个较大值，入栈，寻找当前值的下一个较大值
      //  遍历完 nums2 数组，任然没有找到栈顶的下一个较大值，说明数字是降序序列，结束
      while (!stack.isEmpty() && stack.peek() < nums2[i]){
        map.put(stack.pop(),nums2[i]);
      }
      stack.push(nums2[i]);
    }
    for (int i = 0; i < nums1.length; i++) {
      list[i] = map.getOrDefault(nums1[i],-1);
    }
    return list;
  }


}
