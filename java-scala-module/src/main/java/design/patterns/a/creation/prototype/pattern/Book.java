package design.patterns.a.creation.prototype.pattern;

/**
 * @description:
 * @author: tangrenxin
 * @create: 2021-08-27 00:38
 */
public class Book implements Cloneable{
    public String id;
    public String name;

    @Override
    public Book clone() throws CloneNotSupportedException {
        return (Book) super.clone();
    }

}
