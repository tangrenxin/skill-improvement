package design.patterns.b.structural.decorator.pattern;

/**
 * @Description: 具体的服饰类
 * 帽子类
 * @Author: tangrenxin
 * @Date: 2021/8/24 17:29
 */
public class Hats extends PersonDecorator {

  /**
   * 设置 Componet 即 personComponet
   *
   * @param personComponet
   */
  public Hats(PersonComponet personComponet) {
    super(personComponet);
  }

  /**
   * 首先运行原Componet的operation()
   * 再执行本类的功能
   */
  @Override
  public void show() {
    super.show();
    System.out.print(" 小米大红帽 ");
  }
}
