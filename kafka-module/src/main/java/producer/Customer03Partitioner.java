package producer;

import java.util.Map;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

/**
 * @Description: 自定义分区的 producer
 * @Author: tangrenxin
 * @Date: 2021/10/24 22:08
 */
public class Customer03Partitioner implements Partitioner {

  public static void main(String[] args) {

  }

  // 当 partition（）方法中需要用到 configs 中的配置是，需要在configure()方法中将configs 传到configMap 里
  private Map configMap = null;

  public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes,
      Cluster cluster) {
    // 自定义分区的实现
    return 0;
  }

  public void close() {
    // 涉及到一些资源需要关闭时，可以在这里关闭

  }

  public void configure(Map<String, ?> configs) {
    configMap = configs;
  }

}
