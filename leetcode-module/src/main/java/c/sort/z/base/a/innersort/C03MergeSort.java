package c.sort.z.base.a.innersort;

import java.util.Arrays;

/**
 * @Description:
 * 【归并排序】
 *  博客：https://blog.csdn.net/weixin_41725746/article/details/93080926
 *  归并排序（MERGE-SORT）是利用归并的思想实现的排序方法，该算法采用经典的分治（divide-and-conquer）策略
 *  分治法将问题分(divide)成一些小的问题然后[递归]求解，而治的阶段则将分的阶段得到的各答案"修补"在一起，即分而治之
 *  步骤：
 *  1.递归切分数组，将每段数组切成两份，直到不能切了为止（arr.length < 2）
 *    1）找到数组的中间位置，将两个数组一分为二
 *  2.归并两个子数组的数据
 *    1）归并时，需要三块空间：子数组left、子数组right、结果数组 result
 *    2）需要注意：第一次归并时，left和right中都只有一个元素，归并后进入下一次归并时，left和right已经是有序的数列
 *    3）同时遍历left和right，分别定义left和right当前遍历到的元素下标为lIndex和rIndex，结果数组的下标为resIndex
 *    4）left[lIndex]与right[rIndex]较小者插入result，较小者下标+1，继续循环
 *    5）当 left和right 的其中一个下标等于数组长度时，结束循环
 *    6）结束循环后，说明至少有一个数组的数完成了遍历，并插入result数组，但另外一个数组可能还有元素没有遍历到，
 *       由于left和right内部已经有序，直接按照对应的index顺序读取写入result即可
 *
 * 时间复杂度：O(nlogn)
 *    分析：主要考虑两个函数的花销
 *    1.数组划分函数：sort()
 *    2.有序数组归并函数merge()
 *    二者的关系为merge(sort(left), sort(right))，所以二者的时间复杂度应该是乘积的关系
 *    1）先看merge()中，有三个循环，但是没有嵌套，所以时间复杂度是O(n)
 *    2）sort()中，每次需要将一个数组分成两个，那对于length=n的数组，需要被划分多少次k才能得到元素为1的数组呢?
 *      即 n * (1/2)^k = 1,这个其实就跟二分查找的计算方式一样，最后得到的k=logn,这个次数k就是sort()函数的时间复杂度
 *    3）所以综合来讲，归并算法的时间复杂度是 O(n) * O(logn) = O(nlogn)
 *
 * 空间复杂度：O(n)
 *    分析：归并的空间复杂度就是那个临时的result数组n和递归时压入栈的空间logn：n+logn；所以空间复杂度是O(n)
 *
 * 总结：
 *    归并排序虽然比较稳定，在时间上也是非常有效的（最差时间复杂度和最优时间复杂度都是O(nlogn)），但是这种算法很消耗空间，
 *    一般来说，在内部排序中不会用这种方法，而是用快速排序；外部排序才会考虑使用这种方法。
 *
 * @Author: tangrenxin
 * @Date: 2021/10/31 21:30
 */
public class C03MergeSort {

  public static void main(String[] args) {
    int[] nums = {8, 4, 5, 7, 1, 3, 6, 2};
    int[] res = sort(nums);
    for (int re : res) {
      System.out.print(re + ",");
    }
  }

  /**
   * 递归切分数组，将每段数据切成两份，直到不能切未为止(arr.length < 2)
   * @param nums
   * @return
   */
  private static int[] sort(int[] nums) {
    int[] arr = Arrays.copyOf(nums, nums.length);
    // 当前数组已经不可再拆
    if (arr.length < 2) {
      return arr;
    }
    // 找到数组的中间位置，然后将数组一分为二
    int middle = (int) Math.floor(arr.length / 2);
    int[] left = Arrays.copyOfRange(arr, 0, middle);
    int[] right = Arrays.copyOfRange(arr, middle, arr.length);
    // sort(left),sort(right) 递归不断的进行拆分，直到不能拆为止
    // merge 进行归并 返回
    return merge(sort(left), sort(right));
  }

  /**
   * 归并两个子数组的数据
   * @param left
   * @param right
   * @return
   */
  private static int[] merge(int[] left, int[] right) {
    //新数组，用于存放 left 和 right 合并后的数据
    int[] result = new int[left.length + right.length];
    // 结果数组下标
    int resIndex = 0;
    // 左表当前遍历到的元素下标
    int lIndex = 0;
    // 右表当前遍历到的元素下标
    int rIndex = 0;
    // 两个数组当前所指的元素进行比较，小的那一个先入结果数组，小的index++，结束一次循环,进入下一次循环
    // 当任意一个数组的所有元素都被遍历完加入到结果数组时，结束循环
    while (lIndex < left.length && rIndex < right.length) {
      // 比较左右表当前的元素，较小者插入结果数组，较小者index++
      if (left[lIndex] < right[rIndex]) {
        result[resIndex++] = left[lIndex];
        lIndex++;
      } else {
        result[resIndex++] = right[rIndex];
        rIndex++;
      }
    }
    // 上面的循环结束，说明至少有一个数组的元素被遍历完，但不知道具体是哪一个，但是可以通过各自的index判断
    while (lIndex < left.length) {
      result[resIndex++] = left[lIndex];
      lIndex++;
    }
    while (rIndex < right.length) {
      result[resIndex++] = right[rIndex];
      rIndex++;
    }
    return result;
  }
}
