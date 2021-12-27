package design.patterns.b.structural.proxy.pattern;

/**
 * 被代理的实体类
 */
public class RealImage implements Image{

    private String fileName;

    public RealImage(String fileName){
        this.fileName = fileName;
        // 实体类特有方法被调用，说明代理使用的是被代理(本类)的对象方法
        loadFromDisk(fileName);
    }

    /**
     * 实体类的特有方法
     * @param fileName
     */
    private void loadFromDisk(String fileName){
        System.out.println("Loading "+fileName);
    }


    /**
     * 实现抽象的方法
     */
    public void display() {
        System.out.println("Displaying " + fileName);
    }
}
