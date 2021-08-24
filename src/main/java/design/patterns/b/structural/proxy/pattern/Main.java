package design.patterns.b.structural.proxy.pattern;

/**
 * @description: 客户端
 * @author: tangrenxin
 * @create: 2021-08-25 00:51
 */
public class Main {
    public static void main(String[] args) {
        // 获取代理对象（功能:展示图片）
        Image image = new ProxyImage("test_10mb.jpg");

        // 图像将从磁盘加载：第一次调用display()， ProxyImage中的realImage对象为空，需要new
        image.display();
        System.out.println("");
        // 图像不需要从磁盘加载：第二次调用display()， ProxyImage中的realImage对象不为空，之间展示
        image.display();
    }
}
