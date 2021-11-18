package design.patterns.a.creation.prototype.pattern;

/**
 * @description: 创建一个实现了 Cloneable 接口的抽象类
 * @author: tangrenxin
 * @create: 2021-08-27 00:11
 */
public abstract class Shape implements Cloneable{
    private String id;
    protected String type;
    public Book book;

    abstract void draw();

    public String getType(){
        return type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * 浅拷贝
     * @return
     */
//    @Override
//    public Object clone() {
//        Object clone = null;
//        try {
//            clone = super.clone();
//        } catch (CloneNotSupportedException e) {
//            e.printStackTrace();
//        }
//        return clone;
//    }


    /**
     * 深拷贝
     * @return
     */
    @Override
    public Shape clone() {
        Shape clone = null;
        try {
            clone = (Shape) super.clone();
            clone.book = this.book.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }


}
