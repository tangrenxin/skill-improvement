package c.sort.a.easy;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/9/9 01:18
 */
public class Test {
  public static void main(String[] args) {
    String str = "";
    String[] lines = str.split("\n");
    for (String line : lines) {
      if(line.contains("\t") || line.contains("%")){
        continue;
      }
      System.out.println(line);
    }


  }


}
