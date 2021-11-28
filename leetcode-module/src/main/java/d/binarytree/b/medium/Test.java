package d.binarytree.b.medium;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/11/28 下午2:24
 */
public class Test {

  public static void main(String[] args) {

    Deque<Integer> path = new LinkedList<>();
    path.offerLast(1);
    path.offerLast(2);
    path.offerLast(3);

    Deque<Integer> tmpPath = new LinkedList<>(path);
    tmpPath.pollFirst();
    System.out.println(path);
    System.out.println(tmpPath);


    int sum = 100;

    int tmp = sum;
    tmp -= 30;

    System.out.println(sum);
    System.out.println(tmp);
  }
}
