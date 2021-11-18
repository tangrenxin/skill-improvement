package a.thread.b.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/11/18 16:13
 */
public class Test {

  public static void main(String[] args) {
    ThreadPoolExecutor executor = new ThreadPoolExecutor(
        5,
        10,
        200,
        TimeUnit.MILLISECONDS,
        new ArrayBlockingQueue<Runnable>(5));

    for (int i = 0; i < 20; i++) {
      // 创建 task
      MyTask myTask = new MyTask(i);
      // 向线程池中提交一个任务
      executor.execute(myTask);
      System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +
          executor.getQueue().size() + "，已执行完毕的任务数目：" + executor.getCompletedTaskCount());
    }

  }
}

class MyTask implements Runnable {

  private int taskNum;

  public MyTask(int num) {
    this.taskNum = num;
  }

  public void run() {
    System.out.println("正在执行task " + taskNum);
    try {
      Thread.sleep(4000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("task " + taskNum + "执行完毕");
  }
}
