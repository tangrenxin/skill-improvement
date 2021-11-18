package a.thread.a.createthread;

/**
 * @Description:
 * 1.通过实现 Runnable 接口创建多线程
 * @Author: tangrenxin
 * @Date: 2021/11/18 14:45
 */
public class Code01CreateThread {

  public static void main(String[] args) {
    RunnableDemo R1 = new RunnableDemo( "Thread-1");
    R1.start();

    RunnableDemo R2 = new RunnableDemo( "Thread-2");
    R2.start();
  }
}

class RunnableDemo implements Runnable {

  private Thread t;
  private String threadName;

  public RunnableDemo(String threadName) {
    this.threadName = threadName;
    System.out.println("Creating " + threadName);
  }

  public void start() {
    System.out.println("Starting " + threadName);
    if (t == null) {
      t = new Thread(this, threadName);
      t.start();
    }
  }

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


