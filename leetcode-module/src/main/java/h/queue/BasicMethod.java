package h.queue;

/**
 * @Description:
 *
 * @Author: tangrenxin
 * @Date: 2021/10/26 21:07
 */
public class BasicMethod {

  /**
   * 获取头元素的方法
   * 1.获取并移除
   *     poll() 　　  获取并移除此队列的头，如果此队列为空，则返回 null
   *     remove()　　 获取并移除此队列的头，如果此队列为空，则抛出NoSuchElementException异常
   * 2.获取但不移除
   *     peek()　　   获取队列的头但不移除此队列的头。如果此队列为空，则返回 null
   *     element()　　获取队列的头但不移除此队列的头。如果此队列为空，则将抛出NoSuchElementException异常
   * 添加元素的方法
   *     offer()　    将指定的元素插入此队列（如果立即可行且不会违反容量限制），插入成功返回 true；否则返回 false。
   *                  当使用有容量限制的队列时，offer方法通常要优于 add方法——add方法可能无法插入元素，而只是抛出一个
   *                  IllegalStateException异常
   *     add()　　     将指定的元素插入此队列
   */
}
