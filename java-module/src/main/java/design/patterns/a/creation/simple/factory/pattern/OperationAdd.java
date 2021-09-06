package design.patterns.a.creation.simple.factory.pattern;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/8/23 17:32
 */
public class OperationAdd extends Operation {

  @Override
  public double getResult() {
    double result = 0;
    result = numberA + numberB;
    return result;
  }
}
