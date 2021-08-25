package design.patterns.a.creation.factory.method.pattern.eg1;


/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/8/23 17:44
 */
public class Main {

  public static void main(String[] args) throws Exception {
    OperationFactory addFactory = new AddFactory();
    Operation operation = addFactory.createOperate();
    operation.setNumberA(10);
    operation.setNumberB(20);
    System.out.println("计算结果为："+operation.getResult());

    OperationFactory subFactory = new SubFactory();
    Operation subOperation = subFactory.createOperate();
    subOperation.setNumberA(10);
    subOperation.setNumberB(20);
    System.out.println("计算结果为："+subOperation.getResult());
  }
}
