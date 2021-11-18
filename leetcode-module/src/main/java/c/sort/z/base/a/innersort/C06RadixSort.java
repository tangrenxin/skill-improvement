package c.sort.z.base.a.innersort;

import java.util.ArrayList;
import java.util.Random;

/**
 * @Description:
 *【基数排序】
 * 基数排序(Radix Sort)是桶排序的扩展，它的基本思想是：将整数按位数切割成不同的数字，然后按每个位数分别比较。
 * 具体做法是：
 *    将所有待比较数值统一为同样的数位长度，数位较短的数前面补零。
 *    然后，从最低位开始，依次进行一次排序。这样从最低位排序一直到最高位排序完成以后, 数列就变成一个有序序列。
 * 对于3个元素的数组[977, 87, 960]
 * 初始状态 -> 按"个位"排序  ->  按"十位"排序 ->  按"百位"排序
 *  997          960            960              087
 *  087          997            087              960
 *  960          087            997              997
 *
 * 时间复杂度：O(d*n)或O(n) 其中d是数组中最大元素的最高位数，n为元素个数
 *    分析：1.创建桶，恒定 10 个，复杂度是 常数
 *         2.遍历d次数组n，复杂度是 d*n
 *         3.每 d 轮遍历需要将桶数组里的数据更新到 nums，复杂度 d*n
 *         综上所述，时间复杂度为 常数+2dn即 O(d*n)
 *
 * 空间复杂度：O(n)
 *    分析：该算法的空间复杂度就是在分配元素时，使用的桶空间；所以空间复杂度为：O(10*n)= O(n)
 *
 * @Author: tangrenxin
 * @Date: 2021/11/1 11:15
 */
public class C06RadixSort {

  public static void main(String[] args) {
    Random rand = new Random();
    int[] nums = new int[20];
    System.out.print("初始数组：");
    for (int i = 0; i < 20; i++) {
      // 生成 20个 [0 - 999] 的随机数
      nums[i] = rand.nextInt(1000);
      System.out.print(nums[i] + " ");
    }
    System.out.println();
    // 由于是 [0 - 999] 的随机数，位数 d = 3
    int[] res = radixSort(nums, 3);
    System.out.print("排序数组：");
    for (int i = 0; i < res.length; i++) {
      System.out.print(res[i] + " ");
    }
  }

  private static int[] radixSort(int[] nums, int d) {
    // 1.因为每个位数上的取值是 [0,9]，直接创建 10 个桶，并初始化
    ArrayList<ArrayList<Integer>> bucketLists = new ArrayList<>(10);
    for (int i = 0; i < 10; i++) {
      bucketLists.add(new ArrayList<>());
    }
    // 2.分别遍历 个位、十位、百位...d位
    for (int i = 0; i < d; i++) {
      for (int j = 0; j < nums.length; j++) {
        // 提取当前 位上的数字作为桶的 index
        int bucketIndex = (int) (nums[j] / Math.pow(10, i) % 10);
        bucketLists.get(bucketIndex).add(nums[j]);
      }
      // 3.完成一趟遍历后更新 nums 的排列顺序
      int index = 0;
      for (ArrayList<Integer> bucketList : bucketLists) {
        for (int j = 0; j < bucketList.size(); j++) {
          nums[index++] = bucketList.get(j);
        }
        // 数据提取后，将桶中的数据清零
        bucketList.clear();
      }
    }
    return nums;
  }

}
