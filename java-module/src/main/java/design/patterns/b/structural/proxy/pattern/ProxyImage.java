package design.patterns.b.structural.proxy.pattern;

/**
 * @description: RealImage 实体类的代理类
 * @author: tangrenxin
 * @create: 2021-08-25 00:47
 */
public class ProxyImage implements Image{

    // 声明被代理类的引用
    private RealImage realImage;
    private String fileName;
    public ProxyImage(String fileName){
        this.fileName = fileName;
    }

    /**
     * 实现接口的方法
     */
    public void display() {
        if(realImage == null){
            realImage = new RealImage(fileName);
        }
        realImage.display();
    }
}
