package a.array.b.medium;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @Description:
 * 954. 二倍数对数组
 *
 * 给定一个长度为偶数的整数数组 arr，只有对 arr 进行重组后可以满足
 * “对于每个 0 <= i < len(arr) / 2，都有 arr[2 * i + 1] = 2 * arr[2 * i]” 时，返回 true；否则，返回 false。
 *
 * 示例 1：
 *
 * 输入：arr = [3,1,3,6]
 * 输出：false
 *
 * 示例 2：
 *
 * 输入：arr = [2,1,2,6]
 * 输出：false
 *
 * 示例 3：
 *
 * 输入：arr = [4,-2,2,-4]
 * 输出：true
 * 解释：可以用 [-2,-4] 和 [2,4] 这两组组成 [-2,-4,2,4] 或是 [2,4,-2,-4]
 *
 * 提示：
 *
 *     0 <= arr.length <= 3 * 104
 *     arr.length 是偶数
 *     -105 <= arr[i] <= 105
 * @Author: tangrenxin
 * @Date: 2022/4/1 10:18
 */
public class LC954CanReorderDoubled {

  public static void main(String[] args) {

//    int[] arr = {3,1,3,6};
//    int[] arr = {2,1,2,6};
//    int[] arr = {4,-2,2,-4};
    int[] arr = {2,4,0,0,8,1};
    System.out.println(canReorderDoubled(arr));

  }

  public static boolean canReorderDoubled(int[] arr) {
    // 这里的排序很关键，如果不排序，测试用例：{2,4,0,0,8,1} 就过不了
    // 因为 1 2 4 8 中，按照原顺序  2 4 会先匹配上，这样的话  后面的 8 1 就没办法匹配了
    // 如果先排序，arr 变成 {0,0,1,2,4,8} 这样匹配就没问题了
    Arrays.sort(arr);
    // <arr中的元素,数量>
    HashMap<Integer, Integer> arrMap = new HashMap<>();
    for (int i = 0; i < arr.length; i++) {
      if (arrMap.containsKey(arr[i])) {
        arrMap.put(arr[i], arrMap.get(arr[i]) + 1);
      } else {
        arrMap.put(arr[i], 1);
      }
    }
    // 配对上的 将对应数字 的数量-1
    for (int i = 0; i < arr.length; i++) {
      int num = arr[i];
      int num2 = 2 * num;
      if(arrMap.get(num) > 0 && arrMap.containsKey(num2) && arrMap.get(num2) > 0){
        arrMap.put(num,arrMap.get(num) - 1);
        arrMap.put(num2,arrMap.get(num2) - 1);
      }
    }

    // 如果存在某个数字的 数量 > 0 说明至少有一对没有匹配上
    for (Integer integer : arrMap.keySet()) {
      if(arrMap.get(integer) > 0){
        return false;
      }
    }
    return true;
  }
}
