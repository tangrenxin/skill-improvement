package c.sort.z.base.a.innersort;

/**
 * @Description:
 * 【快速排序】
 *  实现思路
 *  方式一：双指针
 *  1.选择一个基准元素 pivot，两个指针：start、end 分别指向数组的头和尾
 *  2.从数组的尾部开始遍历数组，将小于或等于pivot的数放在pivot的左边，大于的数放在右边
 *    实现：1）从 end 开始循环比较，大于pivot则end--，继续循环到比 pivot 小的数，与 start 指向的位置交换
 *         2）交换循环方向
 *         3）从 start 开始循环比较，小于pivot则start++，继续循环到比 pivot 大的数，与 end 指向的位置交换
 *         4）直到 start = end 时，将start指向的位置填充为pivot，结束循环，完成这一轮的遍历，并返回当前pivot的
 *         位置，作为下一轮遍历的 基准元素下标。
 *  3.将上一步得到的基准元素下标，将数组逻辑上分成两个子数组进行递归调用，直到数组不能再拆为止。
 *
 *  方式二：单指针
 *  1.选择一个基准元素 pivot，同时设置mark指针指向起始位置，这个mark指针代表 小于基准元素的区域边界
 *  2.从基准元素的下一个位置开始遍历，如果遍历到的元素大于pivot，则继续向后遍历，如果小于pivot，需要做两件事：
 *      1）mark指针右移一位
 *      2）让最新遍历到的元素和mark指针所在位置的元素交换位置
 *  3.最后把基准元素交换到mark指向的位置，这一轮结束，返回基准元素的下标
 *  4.递归调用
 *
 * @Author: tangrenxin
 * @Date: 2022/4/19 17:14
 */
public class C09QuickSort {

  public static void main(String[] args) {
    int[] nums = {5, 1, 2, 3, 6, 4};
    // 方式一：双指针
    //quickSort1(nums, 0, nums.length - 1);
    // 方式二：单指针
    quickSort2(nums, 0, nums.length - 1);

    for (int i = 0; i < nums.length; i++) {
      System.out.print(nums[i] + ",");
    }
  }

  /**
   * 方式二：单指针
   * @param nums
   * @param startIndex
   * @param endIndex
   */
  private static void quickSort2(int[] nums, int startIndex, int endIndex) {

    if (startIndex >= endIndex) {
      return;
    }
    // 1.进行一轮排序分区，并获取本次排序结束后的 基准元素 的下标
    int pivotIndex = partitionSort2(nums, startIndex, endIndex);
    // 2.递归排序 pivotIndex 左边的部分
    quickSort2(nums, startIndex, pivotIndex - 1);
    // 3.递归排序 pivotIndex 右边的部分
    quickSort2(nums, pivotIndex + 1, endIndex);

  }

  private static int partitionSort2(int[] nums, int startIndex, int endIndex) {
    // 1.基准元素，mark指针
    int pivot = nums[startIndex];
    int mark = startIndex;
    // 2.从基准元素的下一个位置开始遍历，遇到比 pivot 大的数，不做处理
    for (int i = startIndex + 1; i <= endIndex; i++) {
      // 2.1遇到比 pivot 小的数，执行下面的操作
      if (nums[i] < pivot){
        mark++;
        int tmp = nums[mark];
        nums[mark] = nums[i];
        nums[i] = tmp;
      }
    }
    // 3.最后把基准元素交换到mark指向的位置，这一轮结束，返回基准元素的下标
    nums[startIndex] = nums[mark];
    nums[mark] = pivot;
    return mark;
  }

  /**
   * 方式一：双指针
   * @param nums
   * @param startIndex
   * @param endIndex
   */
  private static void quickSort1(int[] nums, int startIndex, int endIndex) {
    if (startIndex >= endIndex) {
      return;
    }
    // 1.进行一轮排序分区，并获取本次排序结束后的 基准元素 的下标
    int pivotIndex = partitionSort(nums, startIndex, endIndex);
    // 2.递归排序 pivotIndex 左边的部分
    quickSort1(nums, startIndex, pivotIndex - 1);
    // 3.递归排序 pivotIndex 右边的部分
    quickSort1(nums, pivotIndex + 1, endIndex);
  }

  private static int partitionSort(int[] nums, int startIndex, int endIndex) {
    // 1.确定本轮分区的基准元素，并定义两个指针
    int pivot = nums[startIndex];
    int start = startIndex;
    int end = endIndex;
    // 2.从数组的尾部开始遍历数组，将小于或等于pivot的数放在pivot的左边，大于的数放在右边
    while (start < end) {
      // 2.1 先比较高位的数，如果高位的数>=基数时，end 左移
      while (start < end && nums[end] >= pivot) {
        end--;
      }
      // 2.2 高位的数<基数时，与 start 交换，然后转向 进入 start循环
      nums[start] = nums[end];

      // 2.3 转向 进入 start 循环 如果低位的数<=基数时，start 右移
      while (start < end && nums[start] <= pivot) {
        start++;
      }

      // 2.4 低位的数>基数时，与 end 交换，然后转向 进入 end 循环
      nums[end] = nums[start];
    }
    // 3. 当start = end 时，循环结束，将pivot填充至 start 指向的位置，并返回下标
    nums[start] = pivot;
    return start;
  }
}
