package design.patterns.a.creation.simple.factory.pattern;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/8/23 17:39
 */
public class OperationFactory {

  public static Operation createOperate(char operate) {
    Operation operation;
    switch (operate) {
      case '+':
        operation = new OperationAdd();
        break;
      case '-':
        operation = new OperationSub();
        break;
      case '*':
        operation = new OperationMul();
        break;
      case '/':
        operation = new OperationDiv();
        break;
      default:
        operation = null;
        break;
    }
    return operation;
  }
}
