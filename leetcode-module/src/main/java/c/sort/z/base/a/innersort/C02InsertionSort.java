package c.sort.z.base.a.innersort;

/**
 * @Description:
 * 【插入排序】步骤：
 * 1.外循环，遍历数组得到当前元素i
 * 2.内循环，假设i前面的i-1个数已经排好序了，遍历当前元素i以前的元素，将i插入排好的序列中，然后找到合适自己的位置
 * 3.照此法对所有元素进行插入，直到整个序列排为有序
 *
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(1)
 * @Author: tangrenxin
 * @Date: 2021/10/31 21:14
 */
public class C02InsertionSort {

  public static void main(String[] args) {
    int[] nums = {8, 4, 5, 7, 1, 3, 6, 2};
    int[] res = sort(nums);
    for (int re : res) {
      System.out.print(re + ",");
    }
  }

  private static int[] sort(int[] nums) {
    int tmp;
    // 外循环，遍历数组
    for (int i = 0; i < nums.length; i++) {
      // 内循环，遍历当前元素以前的数据，分别于当前元素比较，进行交换
      for (int j = i; j > 0; j--) {
        if (nums[j] < nums[j - 1]) {
          tmp = nums[j - 1];
          nums[j - 1] = nums[j];
          nums[j] = tmp;
        } else {
          // 由于当前元素之前的数据已经是有序的，当前元素大于 前一个元素时，一定比之前的元素都大，直接break
          break;
        }
      }
    }
    return nums;
  }
}
