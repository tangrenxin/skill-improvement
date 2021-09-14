package aaaaaa;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/9/13 21:33
 */
public class Test {

  public static void main(String[] args) {
    String number = "10000000003409.50";
    String afterStr = ConvertUpMoney.toChinese(number);
    System.out.println(number + ": " + afterStr);//壹仟肆佰零玖元伍角
    System.out.println(number.substring(0,2));
  }




}
