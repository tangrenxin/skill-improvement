package c.sort.z.base.a.innersort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * @Description:
 *【桶排序】
 * 桶排序同样是一种线性时间的排序算法。类似于计数排序所创建的统计数组，桶排序需要创建若干个桶来协助排序。
 * 每一个桶代表一个区间范围，里面可以承载一个或多个元素。
 *
 * 一句话总结：划分多个范围相同的区间，每个子区间自排序，最后合并
 *
 * 桶排序是计数排序的扩展版本，计数排序可以看成每个桶只存储相同元素，而桶排序每个桶可以存储一定范围的元素
 * 通过映射函数，将待排序数组中的元素映射到各个对应的桶中，对每个桶中的元素进行排序，最后将非空桶中的元素逐个放入源序列中。
 *
 *  假如有一个非整数的数列：
 *  4.5 0.84 3.25 2.18 0.5
 *  1.计算max/min
 *  2.计算桶的数量 bucketNum，(创建桶数量可以有很多方式,这里以原数组长度为桶的数量)
 *  3.区间跨度:intervalLength=（最大值-最小值）/(桶的数量-1)=(4.5-0.5)/(5-1)=1
 *      所以每个桶的区间范围为1，分别为：桶的编号分别为0到(bucketNum-1)
 *      [0.5,1.5) [1.5,2.5) [2.5,3.5) [3.5,4.5) [4.5,4.5]
 *  4.遍历原始数列，把元素对号入座放入各个桶中
 *    如何确定遍历到的元素应该放到哪个桶中呢?
 *    取具体放到哪个桶的索引值的方法就是拿该元素的偏移量除以区间长度
 *    1) 计算出当前元素arr[i]到偏移量(min)的偏移长度 len = arr[i] - min
 *    2) 桶的编号=偏移长度/桶的区间长度（桶的编号从 0 开始）
 *  5.对每个桶内部的元素分别进行排序
 *  6.遍历所有的桶，输出所有元素。
 *
 *  时间复杂度：O(n)
 *     分析：1.子最大最小值，计算量N
 *          2.初始化桶，由于桶的数量有多种计算方式，假设有M个桶，计算量为M，【如果数据分布均匀，每个桶分到的元素是N/M】
 *          3.数列放入桶中，计算量为N
 *          4.在每个桶内做排序，由于使用了O(nlogn)算法(这里的n应该是每个桶分到的元素个数，也就是N/M)，外层循环是M个桶
 *            所以计算量是M*(N/M*logN/M)=N*logN/M
 *          5.最后输出排序数列计算量是N
 *          综上所述，计算量是 3N+M+N*logN/M
 *          假如 N=M,那 3N+M+N*logN/M = 3N+N+N*logN/N = 4N+N*log1=4N=N，即O(n)
 *
 *  空间复杂度：O(n+m)或O(n)
 *      分析：原始数组n加上桶的空间m，即O(n+m)
 *           假如 N=M,则空间复杂度为O(n)
 *
 * @Author: tangrenxin
 * @Date: 2021/11/1 01:42
 */
public class C05BucketSort {

  public static void main(String[] args) {
    Random rand = new Random();
    int[] nums = new int[20];
    System.out.print("初始数组：");
    for (int i = 0; i < 20; i++) {
      nums[i] = rand.nextInt(100  + 1) + 90;
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
    // TODO 1.计算最大值与最小值
    int max = Integer.MIN_VALUE;
    int min = Integer.MAX_VALUE;
    for (int i = 0; i < arr.length; i++) {
      max = Math.max(max, arr[i]);
      min = Math.min(min, arr[i]);
    }
    // TODO 2.桶的数量=原数组长度（方式一）
    int bucketNum = arr.length;
    // 创建 bucketNum 个桶，桶的编号是0到（bucketNum-1）
    ArrayList<ArrayList<Integer>> bucketLists = new ArrayList<>(bucketNum);
    // 那每个桶的 区间长度=(max-min)/(桶数量-1)
    int intervalLength = (max - min) / (bucketNum - 1);
    // 创建 bucketNum 个空桶
    for (int i = 0; i < bucketNum; i++) {
      bucketLists.add(new ArrayList<>());
    }
    // TODO 3.遍历原始数组，将每个元素放入桶中
    for (int i = 0; i < arr.length; i++) {
      // min 是原数组的最小值，作为偏移量
      // 如何确定元素arr[i]被分到哪个桶呢？
      // 1.计算出当前元素arr[i]到偏移量(min)的偏移长度 len = arr[i] - min
      // 2.桶的编号=偏移长度/桶的区间长度（桶的编号从 0 开始）
      System.out.println(arr[i]);
      int bucketIndex = (arr[i] - min) / intervalLength;
      bucketLists.get(bucketIndex).add(arr[i]);
    }
    // 4.对每个桶内部进行排序
    for (ArrayList<Integer> bucketList : bucketLists) {
      Collections.sort(bucketList);
    }
    int[] sortedArray = new int[arr.length];
    int index = 0;
    // 5.将桶中的元素赋值到结果数组
    for (ArrayList<Integer> bucketList : bucketLists) {
      for (int i = 0; i < bucketList.size(); i++) {
        sortedArray[index++] = bucketList.get(i);
      }
    }
    return sortedArray;
  }

  private static int[] bucketSort2(int[] arr) {
    // TODO 1.计算最大值与最小值
    int max = Integer.MIN_VALUE;
    int min = Integer.MAX_VALUE;
    for (int i = 0; i < arr.length; i++) {
      max = Math.max(max, arr[i]);
      min = Math.min(min, arr[i]);
    }
    // TODO 2.计算桶的数量=差值/原数组长度 + 1(方式二)
    int bucketNum = (max - min) / arr.length + 1;
    ArrayList<ArrayList<Integer>> bucketLists = new ArrayList<>(bucketNum);
    // 创建 bucketNum 个桶
    for (int i = 0; i < bucketNum; i++) {
      bucketLists.add(new ArrayList<>());
    }
    // 那每个桶的 区间长度=(max-min)/(桶数量-1)=arr.length
    int intervalLength = arr.length;
    System.out.println("bucketNum=" + bucketNum + " bucketLists.size=" + bucketLists.size());
    // TODO 3.遍历原始数组，将每个元素放入桶中
    for (int i = 0; i < arr.length; i++) {
      // min 是原数组的最小值，作为偏移量
      // 如何确定元素arr[i]被分到哪个桶呢？
      // 1.计算出当前元素arr[i]到偏移量(min)的偏移长度 len = arr[i] - min
      // 2.桶的编号=偏移长度/桶的区间长度（桶的编号从 0 开始）
      int bucketIndex = (arr[i] - min) / intervalLength;
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
