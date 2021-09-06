package design.patterns.a.creation.factory.method.pattern.eg1;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/8/23 17:30
 */
public class Operation {

  public double numberA = 0;
  public double numberB = 0;

  public double getNumberA() {
    return numberA;
  }

  public void setNumberA(double numberA) {
    this.numberA = numberA;
  }

  public double getNumberB() {
    return numberB;
  }

  public void setNumberB(double numberB) {
    this.numberB = numberB;
  }

  public double getResult() throws Exception {
    double result = 0;
    return result;
  }

}
