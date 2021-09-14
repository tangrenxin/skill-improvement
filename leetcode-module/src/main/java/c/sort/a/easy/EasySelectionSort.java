package c.sort.a.easy;

import java.util.Arrays;

/**
 * @Description:
 * 选择排序
 * @Author: tangrenxin
 * @Date: 2021/9/12 21:18
 */
public class EasySelectionSort {

  public static void main(String[] args) {
    int[] nums = {8, 4, 5, 7, 1, 3, 6, 2};
    int[] res = selectionSort(nums);
    for (int re : res) {
      System.out.print(re + ",");
    }
  }

  /**
   * 选择排序的思想，每次遍历都选出最小的元素
   * 依次按顺序交换数据
   * @param nums
   * @return
   */
  private static int[] selectionSort(int[] nums) {

    int[] arr = Arrays.copyOf(nums, nums.length);
    for (int i = 0; i < arr.length - 1; i++) {
      // 本趟遍历最小值的下标
      int minIndex = i;
      for (int j = i + 1; j < arr.length; j++) {
        if (arr[j] < arr[minIndex]) {
          // 记录目前能找到的最小值元素下标
          minIndex = j;
        }
      }
      // 一趟遍历下来，将找到的最小值和i位置所在的值进行交换
      if (i != minIndex) {
        int tmp = arr[i];
        arr[i] = arr[minIndex];
        arr[minIndex] = tmp;
      }
    }
    return arr;
  }
}
