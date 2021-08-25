package design.patterns.b.structural.decorator.pattern;

/**
 * @Description: 人类装饰类
 *  服饰类
 * @Author: tangrenxin
 * @Date: 2021/8/24 17:07
 */
public class PersonDecorator implements PersonComponet {

  PersonComponet personComponet;

  /**
   * 设置 Componet
   * 即 personComponet
   **/
  public PersonDecorator(PersonComponet personComponet) {
    this.personComponet = personComponet;
  }

  /**
   * 重写 Operation()，实际执行的是 Componet 的 Operation（）
   * 即show()
   */
  public void show() {
    if(this.personComponet != null){
      this.personComponet.show();
    }

  }
}
