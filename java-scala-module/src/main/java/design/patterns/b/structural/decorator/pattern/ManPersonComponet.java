package design.patterns.b.structural.decorator.pattern;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/8/24 17:04
 */
public class ManPersonComponet implements PersonComponet {

  private String name;

  public ManPersonComponet(String name) {
    this.name = name;
  }

  public void show() {
    System.out.println(">>>>>> 男人装扮的-"+name);
  }
}
