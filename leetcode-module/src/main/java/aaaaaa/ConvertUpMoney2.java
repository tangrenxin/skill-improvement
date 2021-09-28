package aaaaaa;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/9/13 22:25
 */
public class ConvertUpMoney2 {

  private static final String[] NUMBERS = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
  private static final String[] IUNIT = {"圆", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟",
      "万", "拾", "佰", "仟"};


  public static void main(String[] args) {
    String number = "102400000090.50";
    String afterStr = ConvertUpMoney.toChinese(number);
    System.out.println("我的：" + toChinese(number));
    System.out.println("参考：" + afterStr);
  }


  /**
   *  假设 输入的字符串数据合法 且为正数
   * @param str
   * @return
   */
  public static String toChinese(String str) {
    //整数部分数字
    String integerStr = str.split("\\.")[0];
    //小数部分数字
    String decimalStr = str.split("\\.").length > 0 ? str.split("\\.")[1] : "";

    //整数部分存入数组  目的是为了可以动态的在字符串数组中取对应的值
    int[] integers = toIntArray(integerStr);

    boolean isWan = isWanUnits(integerStr);
    String res = getChineseInteger(integers, isWan);

    return res;
  }

  public static String getChineseInteger(int[] integers, boolean isWan) {
    StringBuffer buffer = new StringBuffer("");
    // 10
    int length = integers.length;
    // 对于输入的字符串为 "0." 存入数组后为 0
    if (length == 1 && integers[0] == 0) {
      return "";
    }
    // 1000000409.50: 壹拾亿零肆佰零玖元伍角
    // 0123456789
    for (int i = 0; i < length; i++) {
      // 中文数字
      String num = "";
      // 中文单位(进位)
      String unit = "";
      if (integers[i] != 0) {
        num = NUMBERS[integers[i]];
        unit = IUNIT[length - i - 1];
      } else {
        /**
         *  连续几个位置是 0 的情况
         *  如果 当前 0 的位置长度为9(亿)、5(万)、1(圆) 时，需要补充相应的单位
         *  连续的几个零在中文数字中只读一个零，那么最终应该显示哪一个零呢？
         *  当前 0 的下一个位置不为 0 的时候
         *  所以，当前位置 i 是 0 时，计算逻辑需分两种情况
         *  1. i+1的位置 == 0，判断是否需要补充单位,不显示零
         *  2. i+1的位置 != 0，判断是否需要补充单位，并显示零
         */
        // 第一步：判断是否需要补充单位
        if (length - i == 9) {
          // 这行代码其实没必要写，只是为了方便理解
          num = "";
          // 当前 0 的位置 是从右到左从1开始数的第9位（亿位）
          unit = IUNIT[9 - 1];
        } else if (length - i == 5 && isWan) {
          // 这行代码其实没必要写，只是为了方便理解
          num = "";
          // 当前 0 的位置 是从右到左从1开始数的第5位（万位）
          unit = IUNIT[5 - 1];
        } else if (length - i == 1) {
          // 这行代码其实没必要写，只是为了方便理解
          num = "";
          // 当前 0 的位置 是从右到左从1开始数的第1位（个位），也就是最后一个数是 0 时，单位是 圆
          unit = IUNIT[1 - 1];
        }
        // 第二步：判断是否需要显示 零 (length - i) > 1：当前位置非最后一个
        if ((length - i) > 1 && integers[i + 1] != 0) {
          // 零
          num = NUMBERS[0];
        }
      }
      /**
       * "103409.50";
       * 我的：壹拾零万叁仟肆佰零玖圆
       * 参考：壹拾万零叁仟肆佰零玖元伍角
       * 注意，当 补充单位 和 显示 零同时存在时，需要先显示填充的单位
       */
      if ("零".equals(num) && !"".equals(unit)) {
        buffer.append(unit + num);
      } else {
        buffer.append(num + unit);
      }
    }
    return buffer.toString();
  }

  /**
   * 将字符串转为int数组
   * @param number
   * @return
   */
  private static int[] toIntArray(String number) {
    //初始化一维数组长度
    int[] array = new int[number.length()];
    //循环遍历赋值
    for (int i = 0; i < number.length(); i++) {
      array[i] = Integer.parseInt(number.substring(i, i + 1));
    }
    return array;
  }

  private static boolean isWanUnits(String integerStr) {
    int length = integerStr.length();
    if (length > 4) {
      String subInteger = "";
      if (length > 8) {
        subInteger = integerStr.substring(length - 8, length - 4);
      } else {
        subInteger = integerStr.substring(0, length - 4);
      }
      return Integer.parseInt(subInteger) > 0;
    } else {
      return false;
    }
  }

}
