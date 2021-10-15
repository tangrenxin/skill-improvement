package c.sort.b.medium;

import java.util.Arrays;

/**
 * @Description:
 * 16. 最接近的三数之和
 *
 * 给定一个包括 n 个整数的数组 nums 和 一个目标值 target。找出 nums 中的三个整数,
 * 使得它们的和与 target 最接近。返回这三个数的和。假定每组输入只存在唯一答案。
 *
 * 示例：
 *
 * 输入：nums = [-1,2,1,-4], target = 1
 * 输出：2
 * 解释：与 target 最接近的和是 2 (-1 + 2 + 1 = 2) 。
 *
 * 提示：
 *
 *     3 <= nums.length <= 10^3
 *     -10^3 <= nums[i] <= 10^3
 *     -10^4 <= target <= 10^4
 * @Author: tangrenxin
 * @Date: 2021/10/8 16:36
 */
public class LC16ThreeSumClosest {

  public static void main(String[] args) {
//    int[] nums = {-1, 2, 1, -4};
//    int[] nums = {1,1,-1, -1, 3};
//    int target = 1;
//    int[] nums = {1, 2, 4, 8, 16, 32, 64, 128};
//    int target = 82;
//    int[] nums = {1,1,-1,-1,3};
//    int target = -1;
    int[] nums = { -3,1,1,3,4,5};
    int target = 1;
    System.out.println(threeSumClosest(nums, target));
  }

  /**
   *
   * @param nums
   * @param target
   * @return
   */
  public static int threeSumClosest(int[] nums, int target) {

    /**
     * 1.先排序
     * 2.一个中间变量 currSum ,表示当前sum与target最小的差值
     * 3.以currSub 作为 treeSum的target,求值
     */
    Arrays.sort(nums);
    int currSum = nums[0] + nums[1] + nums[2];
    for (int i = 0; i < nums.length; i++) {
      for (int j = i + 1; j < nums.length; j++) {
        // 定义 c 的指针为 k
        int k = nums.length - 1;
//        System.out.println("=>"+nums[i] +","+ nums[j] +","+ nums[k]+"=");
        while (j < k && Math.abs(nums[i] + nums[j] + nums[k] - target) >= Math
            .abs(currSum - target)) {
//          System.out.println("j < k]"+nums[i] +","+ nums[j] +","+ nums[k]+"=");
          k--;
        }
        if (j == k) {
          continue;
        }
        currSum = nums[i] + nums[j] + nums[k];
//        System.out.println(currSum + "(" + (currSum - target) + ")");
      }
    }
    return currSum;
  }
}
