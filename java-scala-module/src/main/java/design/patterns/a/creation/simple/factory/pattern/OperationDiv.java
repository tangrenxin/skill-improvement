package design.patterns.a.creation.simple.factory.pattern;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/8/23 17:33
 */
public class OperationDiv extends Operation {

  @Override
  public double getResult() throws Exception {
    double result = 0;
    if(numberB == 0){
      throw new Exception("除数不能为0");
    } else {
      result = numberA / numberB;
    }
    return result;
  }
}
