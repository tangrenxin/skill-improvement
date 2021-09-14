package c.sort.a.easy;

/**
 * @Description:
 * 插入排序
 * 假设前面 n-1(其中 n>=2)个数已经是排好顺序的，现将第 n 个数插到前面已经排好的序列中，然后找到合适自己的位置，
 * 使得插入第n个数的这个序列也是排好顺序的。
 *
 * 按照此法对所有元素进行插入，直到整个序列排为有序的过程，称为插入排序。
 * @Author: tangrenxin
 * @Date: 2021/9/12 21:33
 */
public class EasyInsertionSort {

  public static void main(String[] args) {
    int[] nums = {8, 4, 5, 7, 1, 3, 6, 2};
    int[] res = sort(nums);
    for (int re : res) {
      System.out.print(re + ",");
    }
  }

  private static int[] sort(int[] nums) {
    // 外循环，遍历数组
    for (int i = 0; i < nums.length; i++) {
      // 内循环，遍历当前元素以前的数据，分别于当前元素比较，进行交换
      for (int j = i; j > 0; j--) {
        if (nums[j] < nums[j - 1]) {
          swap(nums, j, j - 1);
        } else {
          // 由于当前元素之前的数据已经是有序的，当前元素大于 前一个元素时，一定比之前的元素都大，直接break
          break;
        }
      }
    }
    return nums;
  }

  /**
   * 交换数据
   * @param arr
   * @param i
   * @param j
   */
  private static void swap(int[] arr, int i, int j) {
    int tmp = arr[i];
    arr[i] = arr[j];
    arr[j] = tmp;
  }
}
