package design.patterns.a.creation.prototype.pattern;

/**
 * @description:
 *  使用 ShapeCache 类来获取存储在 Hashtable 中的形状的克隆。
 * @author: tangrenxin
 * @create: 2021-08-27 00:21
 */
public class Main {

    public static void main(String[] args) {
        ShapeCache.loadCache();

        Shape clonedShape = (Shape) ShapeCache.getShape("1");
        System.out.println("Shape : " + clonedShape.getType());

        Shape clonedShape2 = (Shape) ShapeCache.getShape("2");
        System.out.println("Shape : " + clonedShape2.getType());

        Shape clonedShape3 = (Shape) ShapeCache.getShape("3");
        System.out.println("Shape : " + clonedShape3.getType());
        System.out.println("Shape hashcode:"+clonedShape3.hashCode());
        System.out.println("book hashcode:"+clonedShape3.book.hashCode());

        Shape clonedShape4 = (Shape) ShapeCache.getShape("3");
        System.out.println("Shape : " + clonedShape4.getType());
        System.out.println("Shape hashcode:"+clonedShape4.hashCode());
        System.out.println("book hashcode:"+clonedShape4.book.hashCode());


    }

}
