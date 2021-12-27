package design.patterns.a.creation.factory.method.pattern.eg1;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/8/23 17:33
 */
public class OperationMul extends Operation {

  @Override
  public double getResult() {
    double result = 0;
    result = numberA * numberB;
    return result;
  }
}
