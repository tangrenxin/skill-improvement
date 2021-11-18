package design.patterns.a.creation.prototype.pattern;

/**
 * @description:
 * @author: tangrenxin
 * @create: 2021-08-27 00:13
 */
public class Rectangle extends Shape{

    public Rectangle(){
        type = "Rectangle";
        book = new Book();
    }

    @Override
    void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}
