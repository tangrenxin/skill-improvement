package a.thread.a.createthread;

/**
 * @Description:
 * 2.通过继承Thread来创建线程
 * @Author: tangrenxin
 * @Date: 2021/11/18 15:11
 */
public class Code02CreateThread {

  public static void main(String[] args) {
    ThreadDemo R1 = new ThreadDemo( "Thread-1");
    R1.start();

    ThreadDemo R2 = new ThreadDemo( "Thread-2");
    R2.start();
  }

}
class ThreadDemo extends Thread {

  private Thread t;
  private String threadName;

  public ThreadDemo(String threadName) {
    this.threadName = threadName;
    System.out.println("Creating " + threadName);
  }

  @Override
  public void start() {
    System.out.println("Starting " + threadName);
    if (t == null) {
      t = new Thread(this, threadName);
      t.start();
    }
  }

  @Override
  public void run() {
    System.out.println("Running " + threadName);
    try {
      for (int i = 4; i > 0; i--) {
        System.out.println("Thread: " + threadName + ", " + i);
        // 让线程睡眠一会
        Thread.sleep(50);
      }
    } catch (InterruptedException e) {
      System.out.println("Thread " + threadName + " interrupted.");
    }
    System.out.println("Thread " + threadName + " exiting.");
  }
}