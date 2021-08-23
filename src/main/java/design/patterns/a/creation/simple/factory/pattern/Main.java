package design.patterns.a.creation.simple.factory.pattern;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/8/23 17:44
 */
public class Main {

  public static void main(String[] args) throws Exception {
    Operation operation = OperationFactory.createOperate('+');
    operation.setNumberA(10);
    operation.setNumberB(20);
    System.out.println("计算结果为："+operation.getResult());
  }
}
