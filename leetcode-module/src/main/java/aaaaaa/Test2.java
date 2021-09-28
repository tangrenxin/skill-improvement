package aaaaaa;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/9/22 03:50
 */
public class Test2 {

  public static void main(String[] args) {
    String str = "1000001001";
    int length = str.length();
    System.out.println(length - 4);
    System.out.println(str.substring(length - 8, length - 4));
  }

}
