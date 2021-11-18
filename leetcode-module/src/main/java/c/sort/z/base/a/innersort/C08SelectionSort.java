package c.sort.z.base.a.innersort;

import java.util.Random;

/**
 * @Description:
 * 【选择排序】
 * 在要排序的一组数中，选出最小（或者最大）的一个数与第1个位置的数交换；然后在剩下的数当中再找最小（或者最大）的与
 * 第2个位置的数交换，依次类推，直到第n-1个元素（倒数第二个数）和第n个元素（最后一个数）比较为止。
 *
 * 时间复杂度：O(n^2)
 *    分析：第一次内循环比较N - 1次，然后是N-2次，N-3次，……，最后一次内循环比较1次。
 *    共比较的次数是 (N - 1) + (N - 2) + … + 1，求等差数列和，
 *    得 (N−1+1)*N/2=N^2/2。
 *
 * 空间复杂度：O(1)
 *
 * @Author: tangrenxin
 * @Date: 2021/11/6 20:05
 */
public class C08SelectionSort {

  public static void main(String[] args) {
    Random rand = new Random();
    int[] nums = new int[20];
    System.out.print("初始数组：");
    for (int i = 0; i < 20; i++) {
      nums[i] = rand.nextInt(10);
      System.out.print(nums[i] + " ");
    }
    System.out.println();
    int[] res = selectionSort(nums);
    System.out.print("排序数组：");
    for (int i = 0; i < res.length; i++) {
      System.out.print(res[i] + " ");
    }
  }

  private static int[] selectionSort(int[] nums) {
    // 当前剩下数组中的最小值下标
    int currMinIndex;
    for (int i = 0; i < nums.length; i++) {
      currMinIndex = i;
      for (int j = i + 1; j < nums.length; j++) {
        if (nums[currMinIndex] > nums[j]) {
          currMinIndex = j;
        }
      }
      // 将剩下数组的最小值放在 i 的位置
      if(currMinIndex != i){
        int tmp = nums[i];
        nums[i] = nums[currMinIndex];
        nums[currMinIndex] = tmp;
      }
    }
    return nums;
  }
}
