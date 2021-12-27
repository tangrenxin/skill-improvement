package design.patterns.a.creation.prototype.pattern;

import design.patterns.a.creation.prototype.pattern.Shape;

/**
 * @description:
 * @author: tangrenxin
 * @create: 2021-08-27 00:13
 */
public class Square extends Shape {

    public Square(){
        type = "Square";
        book = new Book();
    }

    @Override
    void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}
