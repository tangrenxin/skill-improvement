package a.thread.a.createthread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Description:
 * 3.通过 Callable 和 Future 来创建线程
 * 这种创建方式创建的线程可以有返回值
 * 有返回值的线程
 * @Author: tangrenxin
 * @Date: 2021/11/18 15:42
 */
public class Code03CreateThread {

  /**
   * 1.创建Callable 接口的实现类，并实现call()方法，该 call() 方法将作为线程执行体，并且有返回值。
   * 2.【使用】创建Callable 实现类的实例，使用 FutureTask 类来包装 Callable 对象，
   *    该 FutureTask 对象封装了该 Callable 对象的 call（）方法的返回值。
   * 3.使用 FutureTask 对象作为Thread 对象的Target 创建并启动新线程。
   * 4.调用 FutureTask 对象的get() 方法来获得子线程执行结束后的返回值
   * @param args
   */

  public static void main(String[] args) {
    // 【使用】创建Callable实现类的实例
    CallableThreadDemo threadDemo = new CallableThreadDemo();
    // 使用FutureTask类来包装Callable对象，该FutureTask对象封装了该Callable对象的call()方法的返回值
    FutureTask<Integer> futureTask = new FutureTask<Integer>(threadDemo);

    for (int i = 0; i < 100; i++) {
      System.out.println(Thread.currentThread().getName() + " 的循环变量i的值" + i);
      if (i == 20) {
        // 3.使用 FutureTask 对象作为Thread 对象的Target 创建并启动新线程。
        new Thread(futureTask, "有返回值的线程").start();
      }
    }
    try {
      // 4.调用 FutureTask 对象的get() 方法来获得子线程执行结束后的返回值
      System.out.println("子线程的返回值：" + futureTask.get());
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
  }
}

/**
 * 实现Callable<Integer> 接口   Integer是返回值类型
 * 1.创建Callable 接口的实现类，并实现call()方法，该 call() 方法将作为线程执行体，并且有返回值。
 */
class CallableThreadDemo implements Callable<Integer> {

  public Integer call() throws Exception {
    int i = 0;
    for (; i < 100; i++) {
      System.out.println(Thread.currentThread().getName() + " " + i);
    }
    return i;
  }
}



