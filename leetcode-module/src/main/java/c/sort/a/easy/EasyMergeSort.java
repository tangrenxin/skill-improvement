package c.sort.a.easy;

import java.util.Arrays;

/**
 * @Description:
 *
 *  归并算法
 *  https://www.cnblogs.com/chengxiao/p/6194356.html
 * @Author: tangrenxin
 * @Date: 2021/9/12 20:33
 */
public class EasyMergeSort {

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
    if (arr.length < 2) {
      return arr;
    }
    // 找到中间的点，然后一分为二：
    int middle = (int) Math.floor(arr.length / 2);
    // 将一个数组分成两个数组
    int[] left = Arrays.copyOfRange(arr, 0, middle);
    int[] right = Arrays.copyOfRange(arr, middle, arr.length);
    // merge 进行归并
    // left 和 right 还需要进行拆分，直到不能拆为止，使用递归调用
    // 最终使用merge 进行归并 返回
    // 不断的切分、重组
    return merge(sort(left), sort(right));

  }

  /**
   * 归并两个子数组的数据
   *
   * @param left
   * @param right
   * @return
   */
  private static int[] merge(int[] left, int[] right) {
    //新数组，用于存放 left 和 right 合并后的数据
    int[] result = new int[left.length + right.length];
    // 结果数组下标
    int resultIndex = 0;
    // 左表当前遍历到的元素下标
    int leftIndex = 0;
    // 右表当前遍历到的元素下标
    int rightIndex = 0;
    // 两个数组当前所指的元素进行比较，小的那一个先入结果数组，小的index++，结束一次循环,进入下一次循环
    // 当任意一个数组的所有元素都被遍历完加入到结果数组时，结束循环
    while (leftIndex < left.length && rightIndex < right.length) {
      if (left[leftIndex] <= right[rightIndex]) {
        result[resultIndex++] = left[leftIndex];
        leftIndex++;
      } else {
        result[resultIndex++] = right[rightIndex];
        rightIndex++;
      }
    }

    // 上面的循环结束，说明至少有一个数组的元素被遍历完，但是我们并不知道具体是哪一个，但是可以通过各自的德行判断
    // 可能还有数组没有遍历完，在这个地方循环遍历
    while (leftIndex < left.length) {
      result[resultIndex++] = left[leftIndex];
      leftIndex++;
    }
    while (rightIndex < right.length) {
      result[resultIndex++] = right[rightIndex];
      rightIndex++;
    }
    return result;
  }


}
