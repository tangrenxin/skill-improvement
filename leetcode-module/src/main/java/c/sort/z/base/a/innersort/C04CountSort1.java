package c.sort.z.base.a.innersort;

import java.util.Random;

/**
 * @Description:
 * 【计数排序】优化版本
 * 假设数组中有20个随机整数，取值范围为0~10，要求用最快的速度排序。
 *
 * 如果 需要排序的数组是 90 92 99 93 95，按照之前的算法，去最大值99，需要创一个 99+1长的的数组，
 * 可以发现 0- 89是完全用不上的，会造成资源浪费，如何解决这个问题呢？
 * 不再以输入列的最大值+1作为统计数组的长度
 * 而是以 最大值 - 最小值 + 1作为统计数组的长度，
 * 同时列数的最小值作为一个偏移量，用于计算整数在统计数组的下标
 *
 * 以空间换时间的方法，实现排序：
 * 1.遍历待排序数组获得数组中最大的数max和最小值min，建立一个长度为max-min+1的数组countArray。数组下标0~(max-min)，初始值全为0。
 * 2.遍历待排序数组得到元素 a，以a为下标，countArray[a]对应的value+1，表示当前下标对应的数的个数
 * 3.从0~max遍历countArray数组，此时countArray下标i表示数据，对应的value表示有多少个i,最后就输出多少次i，
 *   遍历完就得到原数组的有序数组。
 *
 * @Author: tangrenxin
 * @Date: 2021/10/31 22:36
 */
public class C04CountSort1 {

  public static void main(String[] args) {
    Random rand = new Random();
    int[] nums = new int[20];
    System.out.print("初始数组：");
    for (int i = 0; i < 20; i++) {
      nums[i] = rand.nextInt(10) + 1;
      System.out.print(nums[i] + " ");
    }
    System.out.println();
    int[] res = countSort(nums);
    System.out.print("排序数组：");
    for (int i = 0; i < res.length; i++) {
      System.out.print(res[i] + " ");
    }
  }

  private static int[] countSort(int[] nums) {
    // 1.得到数组最大值，创建countArray的长度是max+1，以保证最后一个下标是max
    int max = nums[0];
    int min = nums[0];
    for (int num : nums) {
      if (max < num) {
        max = num;
      }
      if (min > num) {
        min = num;
      }
    }
    // 2.根据数组最大值，确定统计数组的长度
    int[] countArray = new int[max - min + 1];
    // 3.遍历数组，填充统计数组
    for (int num : nums) {
      countArray[num - min]++;
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
