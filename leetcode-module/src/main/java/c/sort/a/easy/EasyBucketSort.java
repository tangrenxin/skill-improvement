package c.sort.a.easy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * @Description:
 * 桶排序
 * @Author: tangrenxin
 * @Date: 2021/9/14 19:45
 */
public class EasyBucketSort {

  /**
   * 一句话总结：划分多个范围相同的区间，每个子区间自排序，最后合并。
   * 桶排序是计数排序的扩展版本，计数排序可以看成每个桶只存储相同元素，而桶排序每个桶存储一定范围的元素，通过映射函数，
   * 将待排序数组中的元素映射到各个对应的桶中，对每个桶中的元素进行排序，最后将非空桶中的元素逐个放入原序列中。
   *
   * 桶排序需要尽量保证元素分散均匀，否则当所有数据集中在同一个桶中时，桶排序失效。
   * @param args
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
    int[] res = bucketSort(nums);
    System.out.print("排序数组：");
    for (int i = 0; i < res.length; i++) {
      System.out.print(res[i] + " ");
    }
  }

  private static int[] bucketSort(int[] arr) {

    // 1.计算最大值与最小值
    int max = Integer.MIN_VALUE;
    int min = Integer.MAX_VALUE;
    for (int i = 0; i < arr.length; i++) {
      max = Math.max(max, arr[i]);
      min = Math.min(min, arr[i]);
    }
    // 2.计算桶的数量 并初始化桶(重点)
    int bucketNum = (max - min) / arr.length + 1;
    ArrayList<ArrayList<Integer>> bucketLists = new ArrayList<>(bucketNum);
    for (int i = 0; i < bucketNum; i++) {
      bucketLists.add(new ArrayList<>());
    }
    // 3.遍历原始数组，将每个元素放入桶中
    for (int i = 0; i < arr.length; i++) {
      // min 是原数组的最小值，作为偏移量
      int bucketIndex = (arr[i] - min) / arr.length;
      bucketLists.get(bucketIndex).add(arr[i]);
    }

    // 4.对每个桶内部进行排序
    for (int i = 0; i < bucketLists.size(); i++) {
      Collections.sort(bucketLists.get(i));
    }

    // 5.将桶中的元素赋值到结果数组
    int[] sortedArray = new int[arr.length];
    int index = 0;
    for (int i = 0; i < bucketLists.size(); i++) {
      ArrayList list = bucketLists.get(i);
      for (int j = 0; j < list.size(); j++) {
        sortedArray[index++] = (int) list.get(j);
      }
    }
    return sortedArray;
  }
}
