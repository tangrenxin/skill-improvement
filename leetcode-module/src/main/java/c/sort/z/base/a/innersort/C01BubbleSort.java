package c.sort.z.base.a.innersort;

/**
 * @Description:
 * 【冒泡排序】步骤：
 * 1.比较相邻的元素。如果第一个比第二个大（小），就交换他们两个。
 * 2.对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。这步做完后，最后的元素会是最大（小）的数。
 * 3.针对所有的元素重复以上的步骤，除了最后已经选出的元素（有序）。
 * 4.持续每次对越来越少的元素（无序元素）重复上面的步骤，直到没有任何一对数字需要比较，则序列最终有序。
 *
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(1)
 *
 * @Author: tangrenxin
 * @Date: 2021/10/31 21:04
 */
public class C01BubbleSort {

  public static void main(String[] args) {
    int[] nums = {2, 5, 6, 1, 3, 9, 7};
    int[] res = sort(nums);
    for (int re : res) {
      System.out.print(re + ",");
    }
  }

  private static int[] sort(int[] nums) {
    int tmp;
    for (int i = 0; i < nums.length; i++) {
      for (int j = 0; j < nums.length - i - 1; j++) {
        if (nums[j] > nums[j + 1]) {
          tmp = nums[j];
          nums[j] = nums[j + 1];
          nums[j + 1] = tmp;
        }
      }
    }
    return nums;
  }


}
