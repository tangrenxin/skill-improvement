package basics.jvm;

import java.util.ArrayList;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/9/10 15:39
 */
public class Test {

  static class OOMObj {

  }

  public static void main(String[] args) {

    ArrayList<OOMObj> list = new ArrayList<OOMObj>();

    while (true){
      list.add(new OOMObj());
    }


  }
}
