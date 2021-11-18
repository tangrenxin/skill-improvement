package design.patterns.a.creation.prototype.pattern;

/**
 * @description:
 * @author: tangrenxin
 * @create: 2021-08-27 00:13
 */
public class Circle extends Shape{

    public Circle(){
        type = "Circle";
        book = new Book();
    }

    @Override
    void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}
