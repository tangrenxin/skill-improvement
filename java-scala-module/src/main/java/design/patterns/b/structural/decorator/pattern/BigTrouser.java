package design.patterns.b.structural.decorator.pattern;

/**
 * @Description: 具体的服饰类
 * 大裤衩  类
 * @Author: tangrenxin
 * @Date: 2021/8/24 17:19
 */
public class BigTrouser extends PersonDecorator {

  /**
   * 设置 Componet 即 personComponet
   * @param personComponet
   */
  public BigTrouser(PersonComponet personComponet) {
    super(personComponet);
  }

  /**
   * 首先运行原Componet的operation()
   * 再执行本类的功能,如myMethod()，相当于对原Componet进行了装饰
   */
  @Override
  public void show() {
    super.show();
    myMethod();
    System.out.print(" 小米文化大裤衩 ");
  }

  /**
   * 本类特有方法
   */
  private void myMethod(){
    System.out.println("【执行了BigTrouser的特有方法】");
  }
}
