package aaaaaa;

import org.apache.commons.lang.StringUtils;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/9/13 21:22
 */
public class ConvertUpMoneySimple {

  //整数部分的人民币大写
  private static final String[] NUMS = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
  //数位部分 100000409.50
  //元,	拾,	佰,	仟,	万,	拾,	佰,	仟,	亿,	拾,	佰,	仟,	万,	拾,	佰,	仟
  //0 ,	1,	2,	3,	4,	5,	6,	7,	8,	9,	10,	11,	12,	13,	14,	15
  private static final String[] IUNIT = {"元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟",
      "万", "拾", "佰", "仟"};
  //小数部分的人民币大写
  private static final String[] DUNIT = {"角", "分", "厘"};

  //转成中文的大写金额
  public static String toChinese(String str) {

    // TODO 2.输入数据校验，
    //  只包含数字、. 、正负号，
    //  整数部分第一个数是否是0，
    //  如果输入的是 0、0.0、0.00直接返回 零元

    // TODO 3.判断是否存在负号"-"
    boolean flag = false;
    if (str.startsWith("-")) {
      flag = true;
      str = str.replaceAll("-", "");
    }
    // TODO 4.分离整数部分和小数部分
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
    // TODO 5.【整数计算】
    // 1）整数部分超出计算能力，直接返回
    if (integerStr.length() > IUNIT.length) {
      System.out.println(str + "：超出计算能力");
      return str;
    }
    // 2）整数部分存入数组  目的是为了可以动态的在字符串数组中取对应的值
    int[] integers = toIntArray(integerStr);
    //  todo 3）设置万单位
    boolean isWan = isWanUnits(integerStr);
    // 3）小数部分数字存入数组
    int[] decimals = toIntArray(decimalStr);
    // TODO 6.result = 【整数计算】+【小数计算】
    String result = getChineseInteger(integers, isWan) + getChineseDecimal(decimals);

    if (flag) {
      //如果是负数，加上"负"
      return "负" + result;
    } else {
      return result;
    }
  }
  
  /**
   * 【整数计算】
   * 1.没有0的情况，正常输出数字+单位
   * 2.有0的情况
   * 1) 0在13，9，5(iswan)，1时，需要填充万、亿、万、元
   * 2) 第二个万在显示时有个特殊情况
   *    如果 万-亿之间的数全是0，不显示万
   *    如果 万-亿之间的数不全是0，要显示万
   * 3) 对应方法：isWanUnits
   * 4) 什么时候显示 “零”，
   *    (length - i) > 1 && integers[i + 1] != 0
   *
   *  10 0000 0001
   *  有 && isWan 结果: 壹拾亿零壹元伍角 （正确）
   *  无 && isWan 结果: 壹拾亿万零壹元伍角 （错误）
   *
   */
  public static String getChineseInteger(int[] ints, boolean isWan) {
    StringBuffer buff = new StringBuffer("");
    // 10
    int len = ints.length;
    // 1000000409.50: 壹拾亿零肆佰零玖元伍角
    // 0123456789
    for (int i = 0; i < len; i++) {
      String unit = "";
      // TODO 当前位置不是 0
      if(ints[i] != 0){
        buff.append(NUMS[ints[i]] + IUNIT[len - i - 1]);
      } else {
        // TODO 当前位置是 0
        //  0在13，9，5(iswan)，1时，需要填充万、亿、万、元
        if ((len - i) == 13) {
          //万（亿）
          unit = IUNIT[13-1];
        } else if ((len - i) == 9) {
          //亿
          unit = IUNIT[9-1];
        } else if ((len - i) == 5 && isWan) {
          // TODO (len - i) == 5 万-亿之间的数不全是0，要显示万
          //万
          unit = IUNIT[5-1];
        } else if ((len - i) == 1) {
          //元
          unit = IUNIT[1-1];
        }
        // TODO 什么时候填充 零
        if ((len - i) > 1 && ints[i + 1] != 0) {
          unit += NUMS[0];
        }
        buff.append(unit);
      }
    }
    return buff.toString();
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
      chineseDecimal.append(decimals[i] == 0 ? "" : (NUMS[decimals[i]] + DUNIT[i]));
    }
    return chineseDecimal.toString();
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
   * 判断当前整数部分是否已经是达到【万】
   * 对于万前面有 0 的情况
   * 如果 万-亿之间的数全是0，零前面 不显示万
   * 如果 万-亿之间的数不全是0，零前面 要显示万
   *
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