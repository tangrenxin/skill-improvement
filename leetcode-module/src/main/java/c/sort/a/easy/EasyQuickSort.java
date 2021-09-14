package c.sort.a.easy;

/**
 * @Description:
 *  快速排序的两种实现方式
 * @Author: tangrenxin
 * @Date: 2021/9/9 19:19
 */
public class EasyQuickSort {


  public static void main(String[] args) {
    int[] nums = {5, 1, 2, 3, 6, 4};
    // 方式一
//    quickSortDoublePoint(nums, 0, nums.length - 1);

    quickSort(nums,0,nums.length - 1);

    for (int i = 0; i < nums.length; i++) {
      System.out.print(nums[i] + ",");
    }
  }

  /**
   * 第一种方式： 双指针
   * @param nums
   * @param startIndex
   * @param endIndex
   */
  private static void quickSortDoublePoint(int[] nums, int startIndex, int endIndex) {

    if (startIndex >= endIndex) {
      return;
    }
    // 获取 基准元素的下标pivotIndex
    int pivotIndex = partitionDoublePoint(nums, startIndex, endIndex);
    quickSortDoublePoint(nums, startIndex, pivotIndex - 1);
    quickSortDoublePoint(nums, pivotIndex + 1, endIndex);

  }

  private static int partitionDoublePoint(int[] nums, int startIndex, int endIndex) {
    // 选定一个基准元素
    int pivot = nums[startIndex];
    int low = startIndex;
    int high = endIndex;
    // 第一次遍历，low指向第一个元素的位置，并且定为基数，此时low指向的位置相当于是"空"的
    // 我们从开始从 high 开始循环比较，如果比基数大，high--，继续循环直到遇到比基数小的数，与low指向的位置交换，
    // 交换循环方向
    // 产生数据交换后，low右移，进入 low循环，方式与high一样，进入下一个high循环
    while (low < high) {
      // 先比较高位的数，高位的数>=基数时，high 左移
      // 高位的数<基数时,与 low 交换，然后换比较low
      while (low < high && nums[high] >= pivot) {
        high--;
      }
      // 交换数据 此时 high 位置是"空的"
      nums[low] = nums[high];

      while (low < high && nums[low] <= pivot) {
        low++;
      }
      // 交换数据 此时 low 位置是"空的"
      nums[high] = nums[low];
    }
    // low == high 时，循环结束
    // pivot 与 low 指向的位置 交换元素，返回 low
    nums[low] = pivot;
    return low;
  }


  /**
   * 方式二：
   * 单指针实现
   * @param nums
   * @param startIndex
   * @param endIndex
   */
  public static void quickSort(int[] nums, int startIndex, int endIndex) {
    if (startIndex >= endIndex) {
      return;
    }
    // 获取 基准元素的下标pivotIndex
    int pivotIndex = partition(nums, startIndex, endIndex);
    quickSort(nums, startIndex, pivotIndex - 1);
    quickSort(nums, pivotIndex + 1, endIndex);
  }

  /**
   * 获取基准元素的下标pivotIndex
   * 选择第一个元素为基准元素pivot
   * mark 表示小于基准元素的边界，标记小于基准数的最后一个位置，
   * 循环全数组后，基准数还在第一个位置，与mark位置交换，结束本趟，返回mark
   * @param nums
   * @param startIndex
   * @param endIndex
   * @return
   */
  private static int partition(int[] nums, int startIndex, int endIndex) {
    // 选定一个基准元素
    int pivot = nums[startIndex];
    // mark 表示小于基准元素的边界
    int mark = startIndex;
    for (int i = startIndex + 1; i <= endIndex; i++) {
      if (nums[i] < pivot) {
        mark++;
        int tmp = nums[mark];
        nums[mark] = nums[i];
        nums[i] = tmp;
      }
    }
    //最后把基准元素交换到mark指向的位置，这一轮结束
    nums[startIndex] = nums[mark];
    nums[mark] = pivot;
    return mark;
  }





  private static int partition2(int[] nums, int startIndex, int endIndex) {
    // 选定一个基准元素
    int pivot = nums[startIndex];
    // mark 表示小于基准元素的边界
    int mark = startIndex;
    for (int i = startIndex + 1; i <= endIndex; i++) {
      if(nums[i] < pivot){
        mark++;
        if(mark != i){
          int tmp = nums[mark];
          nums[mark] = nums[i];
          nums[i] = tmp;

        }
      }
    }
    //最后把基准元素交换到mark指向的位置，这一轮结束
    nums[startIndex] = nums[mark];
    nums[mark] = pivot;
    return mark;
  }
}

