package aaaaaa;

import org.apache.commons.lang.StringUtils;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/9/13 21:22
 */
public class ConvertUpMoney {

  //整数部分的人民币大写
  private static final String[] NUMBERS = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
  //数位部分 100000409.50
  private static final String[] IUNIT = {"元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟",
      "万", "拾", "佰", "仟"};
  //小数部分的人民币大写
  private static final String[] DUNIT = {"角", "分", "厘"};

  //转成中文的大写金额
  public static String toChinese(String str) {
    //判断输入的金额字符串是否符合要求
    if (StringUtils.isBlank(str) || !str.matches("(-)?[\\d]*(.)?[\\d]*")) {
      System.out.println("抱歉，请输入数字！");
      return str;
    }
    //判断输入的金额字符串
    if ("0".equals(str) || "0.00".equals(str) || "0.0".equals(str)) {
      return "零元";
    }

    //判断是否存在负号"-"
    boolean flag = false;
    if (str.startsWith("-")) {
      flag = true;
      str = str.replaceAll("-", "");
    }
    //如果输入字符串中包含逗号，替换为 "."
    str = str.replaceAll(",", ".");

    String integerStr;//整数部分数字
    String decimalStr;//小数部分数字

    //分离整数部分和小数部分
    if (str.indexOf(".") > 0) {//整数部分和小数部分
      integerStr = str.substring(0, str.indexOf("."));
      decimalStr = str.substring(str.indexOf(".") + 1);
    } else if (str.indexOf(".") == 0) {//只存在小数部分 .34
      integerStr = "";
      decimalStr = str.substring(1);
    } else { //只存在整数部分 34
      integerStr = str;
      decimalStr = "";
    }

    //整数部分超出计算能力，直接返回
    if (integerStr.length() > IUNIT.length) {
      System.out.println(str + "：超出计算能力");
      return str;
    }

    //整数部分存入数组  目的是为了可以动态的在字符串数组中取对应的值
    int[] integers = toIntArray(integerStr);

    //判断整数部分是否存在输入012的情况
    if (integers.length > 1 && integers[0] == 0) {
      System.out.println("抱歉，请输入数字！");
      if (flag) {
        str = "-" + str;
      }
      return str;
    }
    //设置万单位
    boolean isWan = isWanUnits(integerStr);

    //小数部分数字存入数组
    int[] decimals = toIntArray(decimalStr);
    //返回最终的大写金额
    String result = getChineseInteger(integers, isWan) + getChineseDecimal(decimals);

    if (flag) {
      //如果是负数，加上"负"
      return "负" + result;
    } else {
      return result;
    }
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

  /**
   * 将整数部分转为大写的金额
   * @param integers
   * @param isWan
   * @return
   */
  public static String getChineseInteger(int[] integers, boolean isWan) {
    StringBuffer chineseInteger = new StringBuffer("");
    // 10
    int length = integers.length;
    // 对于输入的字符串为 "0." 存入数组后为 0
    if (length == 1 && integers[0] == 0) {
      return "";
    }
    // 1000000409.50: 壹拾亿零肆佰零玖元伍角
    // 0123456789
    for (int i = 0; i < length; i++) {
      //0325464646464
      String key = "";
      if (integers[i] == 0) {
        if ((length - i) == 13) {
          //万（亿）
          key = IUNIT[4];
        } else if ((length - i) == 9) {
          //亿
          key = IUNIT[8];

        } else if ((length - i) == 5 && isWan) {
          //万
          key = IUNIT[4];
        } else if ((length - i) == 1) {
          //元
          key = IUNIT[0];
        }
        if ((length - i) > 1 && integers[i + 1] != 0) {
          key += NUMBERS[0];
        }
      }
      chineseInteger
          .append(integers[i] == 0 ? key : (NUMBERS[integers[i]] + IUNIT[length - i - 1]));
//      System.out.println(chineseInteger.toString());
    }
    return chineseInteger.toString();
  }

  /**
   *
   * @param decimals
   * @return
   */
  private static String getChineseDecimal(int[] decimals) { //角 分 厘   038  壹分捌厘
    StringBuffer chineseDecimal = new StringBuffer("");
    for (int i = 0; i < decimals.length; i++) {
      if (i == 3) {
        break;
      }
      chineseDecimal.append(decimals[i] == 0 ? "" : (NUMBERS[decimals[i]] + DUNIT[i]));
    }
    return chineseDecimal.toString();
  }

  /**
   * 判断当前整数部分是否已经是达到【万】
   * @param integerStr
   * @return
   */
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