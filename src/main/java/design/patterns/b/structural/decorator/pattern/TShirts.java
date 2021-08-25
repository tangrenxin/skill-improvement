package design.patterns.b.structural.decorator.pattern;

/**
 * @Description: 具体的服饰类
 * T-shirt 类
 * @Author: tangrenxin
 * @Date: 2021/8/24 17:19
 */
public class TShirts extends PersonDecorator {

  /**
   * 本类特有功能
   */
  private String addState;
  /**
   * 设置 Componet 即 personComponet
   * @param personComponet
   */
  public TShirts(PersonComponet personComponet) {
    super(personComponet);
  }

  /**
   * 首先运行原Componet的operation()
   * 再执行本类的功能，如addState，相当于对原Componet进行了装饰
   */
  @Override
  public void show() {
    super.show();
    addState = "New state";
    System.out.print(" 小米文化衫 ");
  }
}
