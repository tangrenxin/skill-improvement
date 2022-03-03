package a.array.a.easy;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2022/1/4 18:07
 */
public class Test2 {

  public static void main(String[] args) {
    String install = "208.99\n"
        + "161.80\n"
        + "241.47\n"
        + "235.34\n"
        + "246.60\n"
        + "259.25\n"
        + "228.94\n"
        + "195.53\n"
        + "201.20\n"
        + "279.03\n"
        + "282.50\n"
        + "167.25";

    String sea = "69.04\n"
        + "66.06\n"
        + "73.01\n"
        + "70.78\n"
        + "74.06\n"
        + "76.19\n"
        + "81.34\n"
        + "82.34\n"
        + "74.85\n"
        + "78.05\n"
        + "74.55\n"
        + "76.52";

    String[] instList = sea.split("\n");

    java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
    nf.setGroupingUsed(false);

    String name = "ALL";
    int base = 202200;
    for (int i = 0; i < instList.length; i++) {
      double value = Double.valueOf(instList[i].replace(",", ""));
      System.out.println("(\""+name+"\",\"search\","+(base+i+1)+","+nf.format(value*1000000)+"),");
    }


  }

}
