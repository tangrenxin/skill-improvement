package design.patterns.a.creation.factory.method.pattern.eg1;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/8/25 19:27
 */
public class AddFactory implements OperationFactory {

  private Operation operation;

  public Operation createOperate() {
    if(operation == null ){
      operation = new OperationAdd();
    }
    return operation;
  }
}
