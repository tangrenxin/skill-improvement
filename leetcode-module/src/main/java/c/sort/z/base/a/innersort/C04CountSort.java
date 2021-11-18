package c.sort.z.base.a.innersort;

import java.util.Random;

/**
 * @Description:
 * 【计数排序】
 * 假设数组中有20个随机整数，取值范围为0~10，要求用最快的速度排序。
 *
 * 因为取值范围为0~10，根据这个有限的范围，建立一个长度为11的数组。数组下标0~10，初始值全为0。
 * 以空间换时间的方法，实现排序：
 * 1.遍历待排序数组获得数组中最大的数max，建立一个长度为max+1的数组countArray。数组下标0~max，初始值全为0。
 * 2.遍历待排序数组得到元素 a，以a为下标，countArray[a]对应的value+1，表示当前下标对应的数的个数
 * 3.从0~max遍历countArray数组，此时countArray下标i表示数据，对应的value表示有多少个i,最后就输出多少次i，
 *   遍历完就得到原数组的有序数组。
 *
 * 时间复杂度：O(n+m)
 *    分析：首先，待排序数组应该定义为由n个0~m的整数组成
 *    关注countSort()函数中，1、3、4步都有循环，其中1、3的循环次数是n，
 *    而4的虽然是嵌套的循环，但是内部的循环目的是为了向结果数组中添加i个数字，其实不用循环也可以实现
 *    所以 4 执行的循环次数就是 m，相应的，整个计数排序的时间复杂度是O(n+m)
 *
 * 空间复杂度：O(n+m)
 *    分析：计数数组countArray：m，结果数组resArray：n，总体：O(n+m)
 *
 * 总结：
 *    由于计数数组的长度取决于原数组中最小元素和最大元素，所以当最小元素和最大元素之间的差值比较大时，计数数组将占用很大
 *    的存储空间。因此计数排序算法仅适用于【元素值比较小、元素数量有限而且比较集中】的数组。
 *
 * 该代码可优化，看下一个
 *
 * @Author: tangrenxin
 * @Date: 2021/10/31 22:36
 */
public class C04CountSort {

  public static void main(String[] args) {
    Random rand = new Random();
    int[] nums = new int[20];
    System.out.print("初始数组：");
    for (int i = 0; i < 20; i++) {
      nums[i] = rand.nextInt(10);
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
    for (int num : nums) {
      if (max < num) {
        max = num;
      }
    }
    // 2.根据数组最大值，确定统计数组的长度
    int[] countArray = new int[max + 1];
    // 3.遍历数组，填充统计数组
    for (int num : nums) {
      countArray[num]++;
    }
    // 4.遍历统计数组，输出结果
    // 结果数组的下标
    int index = 0;
    int[] resArray = new int[nums.length];
    for (int i = 0; i < countArray.length; i++) {
      // countArray[i] 的value，表示有几个value i
      for (int j = 0; j < countArray[i]; j++) {
        resArray[index++] = i;
      }
    }
    return resArray;
  }

}
