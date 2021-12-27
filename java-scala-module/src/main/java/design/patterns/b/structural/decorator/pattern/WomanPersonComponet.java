package design.patterns.b.structural.decorator.pattern;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/8/24 17:04
 */
public class WomanPersonComponet implements PersonComponet {

  private String name;

  public WomanPersonComponet(String name) {
    this.name = name;
  }

  public void show() {
    System.out.println(">>>>>> 女人装扮的-"+name);
  }
}
