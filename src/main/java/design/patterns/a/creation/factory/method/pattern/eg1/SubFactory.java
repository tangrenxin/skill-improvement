package design.patterns.a.creation.factory.method.pattern.eg1;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/8/25 19:34
 */
public class SubFactory implements OperationFactory {

  Operation operation;

  public Operation createOperate() {
    if(operation == null){
      operation = new OperationSub();
    }
    return operation;
  }
}
