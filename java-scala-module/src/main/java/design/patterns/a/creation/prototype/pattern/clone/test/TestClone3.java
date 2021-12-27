package design.patterns.a.creation.prototype.pattern.clone.test;

/**
 * @Description: 验证 StringBuffer 等没用重载clone() 并且有final修饰的类 不能实现深度clone
 * 但是 Integer、String、Double等是一特殊情况。
 * @Author: tangrenxin
 * @Date: 2021/8/26 17:27
 */
public class TestClone3 {
  public static void main(String[] args){
    Book book = new Book();
    book.name = new String("Think in Java");
    book.integer = 100;
    book.author = new StringBuffer("Kevin");
    System.out.println("Before clone book.name :"+book.name);
    System.out.println("Before clone book.integer :"+book.integer);
    System.out.println("Before clone book.author :"+book.author);
    Book book_clone = null;
    try{
      book_clone = book.clone();
    }catch(CloneNotSupportedException e){

      e.printStackTrace();
    }
    // 拷贝完后对属性进行修改
    book_clone.name = book_clone.name.substring(0,5);
    book_clone.integer = 200;
    book_clone.author = book_clone.author.append(" Zhang");
    // Think in Java
    System.out.println("\nAfter clone book.name :"+book.name);

    System.out.println("\nAfter clone book.integer :"+book.integer);
    // Kevin Zhang
    System.out.println("After clone book.author :"+book.author);
    // Think
    System.out.println("\nAfter clone book_clone.name :"+book_clone.name);

    System.out.println("\nAfter clone book_clone.integer :"+book_clone.integer);
    // Kevin Zhang
    System.out.println("After clone book_clone.author :"+book_clone.author);

    /**
     * 可以发现，book_clone 对 name 的修改，没有影响到 book.name
     * 而 book_clone 对 author 的修改，影响到了 book.author
     * 说明 book的克隆 没有对StringBuffer类型的author实现深拷贝
     */

    /**
     * 分析：有上述结果可知，Sttring类型的变量看起来好像实现了深度clone，因为对book_clone.name的改动并没有影响到
     * book.name。实质上，在clone的时候book_clone.name与book.name仍然是引用，而且都指向了同一个 String对象。
     * 但在执行book_clone.name = book_clone.name.substring(0,5)的时候，生成了一个新的String类型，然后又赋回
     * 给book_clone.name。这是因为String被 Sun公司的工程师写成了一个不可更改的类（immutable class），在所有
     * String类中的函数都不能更改自身的值。类似的，String类中的其它方法也是如此，都是生成一个新的对象返回。
     * 当然StringBuffer还是原来的对象。
     *
     * 需要知道的是在Java中所有的基本数据类型都有一个相对应的类，例如Integer类对应int类型，Double类对应double类型等
     * 等，这些类也与String类相同，都是不可以改变的类。也就是说，这些的类中的所有方法都是不能改变其自身的值的。这也让我
     * 们在编clone类的时候有了一个更多的选择。同时我们也可以把自己的类编成不可更改的类。
     */

  }
}

class Book implements Cloneable{
  public String name;
  public StringBuffer author;
  public Integer integer;

  @Override
  protected Book clone() throws CloneNotSupportedException{
    return (Book)super.clone();
  }

}
