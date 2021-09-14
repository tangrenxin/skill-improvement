package c.sort.a.easy;

/**
 * @Description:
 *  冒泡排序
 * @Author: tangrenxin
 * @Date: 2021/9/9 19:20
 */
public class EasyBubbleSort {

  public static void main(String[] args) {
    int[] nums = {2, 5, 6, 1, 3, 9, 7};
    doit(nums);
    for (int i = 0; i < nums.length; i++) {
      System.out.print(nums[i] + ",");
    }
  }

  private static void doit(int[] nums) {
    int tmp;
    for (int i = 0; i < nums.length - 1; i++) {
      for (int j = 0; j < nums.length - i - 1; j++) {
        if (nums[j] > nums[j + 1]) {
          tmp = nums[j];
          nums[j] = nums[j + 1];
          nums[j + 1] = tmp;
        }
      }
    }
  }

}
