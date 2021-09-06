package design.patterns.b.structural.decorator.pattern;

/**
 * @Description: 客户端
 * @Author: tangrenxin
 * @Date: 2021/8/24 17:45
 */
public class Main {

  public static void main(String[] args) {

    PersonComponet personComponet = new ManPersonComponet("小明");
    BigTrouser bigTrouser = new BigTrouser(personComponet);
    TShirts tShirts = new TShirts(bigTrouser);
    Hats hats = new Hats(tShirts);
    hats.show();

    System.out.println("");
    WomanPersonComponet womanPersonComponet = new WomanPersonComponet("小芳");
    BigTrouser wbigTrouser = new BigTrouser(womanPersonComponet);
    TShirts wtShirts = new TShirts(wbigTrouser);
    Hats whats = new Hats(wtShirts);
    whats.show();



  }
}
