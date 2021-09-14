package c.sort.a.easy;

import java.util.Random;

/**
 * @Description:
 * 计数排序 优化1
 * @Author: tangrenxin
 * @Date: 2021/9/14 16:19
 */
public class EasyCountSort1 {

  /**
   * 假设数组中有20个随机整数，取值范围为0~10，要求用最快的速度排序。
   * 取值范围为0~10，根据这个有限的范围，简历一个长度为11的数组。
   * 数组下标0~10，初始值全为0
   *
   * 以空间换时间
   *
   * 如果 需要排序的数组是 90 92 99 93 95，按照之前的算法，去最大值99，需要创一个 99+1长的的数组，
   * 可以发现 0- 89是完全用不上的，会造成资源浪费，如何解决这个问题呢？
   * 不再以输入列的最大值+1作为统计数组的长度
   * 而是以 最大值 - 最小值 + 1作为统计数组的长度，
   * 同时列数的最小值作为一个偏移量，用于计算整数在统计数组的下标
   */
  public static void main(String[] args) {
    Random rand = new Random();
    int[] nums = new int[20];
    System.out.print("初始数组：");
    for (int i = 0; i < 20; i++) {
      nums[i] = rand.nextInt(100 - 90 + 1) + 90;
      System.out.print(nums[i] + " ");
    }
    System.out.println();
    int[] res = countSort(nums);
    System.out.print("排序数组：");
    for (int i = 0; i < res.length; i++) {
      System.out.print(res[i] + " ");
    }
    /**
     * 初始数组：7 6 8 4 3 4 7 9 2 5 5 3 9 9 2 7 2 0 2 9
     * 排序数组：0 2 2 2 2 3 3 4 4 5 5 6 7 7 7 8 9 9 9 9
     */

  }

  private static int[] countSort(int[] nums) {
    // 1.得到数组最大值，创建countArray的长度是max+1，以保证最后一个下标是max
    int max = nums[0];
    int min = nums[0];
    for (int i = 1; i < nums.length; i++) {
      if (nums[i] > max) {
        max = nums[i];
      }
      if (nums[i] < min) {
        min = nums[i];
      }
    }
    // 2.根据数组最大值，确定统计数组的长度
    int[] countArray = new int[max - min + 1];
    // 3.遍历数组，填充统计数组
    for (int i = 0; i < nums.length; i++) {
      countArray[nums[i] - min]++;
    }
    // 4.遍历统计数组，输出结果
    // 结果数组的下标
    int index = 0;
    int[] resArray = new int[nums.length];
    for (int i = 0; i < countArray.length; i++) {
      // countArray[i] 的value，表示有几个value i
      for (int j = 0; j < countArray[i]; j++) {
        resArray[index++] = i + min;
      }
    }
    return resArray;
  }

}
