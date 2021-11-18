package producer;

import java.util.Properties;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class Customer03Producer {

  /**
   * 自定义分区的生产者
   * @param args
   * @throws InterruptedException
   */
  public static void main(String[] args) throws InterruptedException {
    // 从 KafkaProducer.java 的注释中提取到下面的simple代码，实现一个简单的消息生产过程
    // 配置信息
    Properties props = new Properties();
    // kafka 集群
    props.put("bootstrap.servers", "localhost:9092");
    // ACK应答级别
    props.put("acks", "all");
    // 消息发送失败后的重试次数
    props.put("retries", 0);
    // 批量大小
    props.put("batch.size", 16384);
    // 提交延迟
    props.put("linger.ms", 1);
    // 缓存，整个producer这边的缓存大小
    props.put("buffer.memory", 33554432);
    // K/V的序列化类
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

    // 设置自定义分区 填写全类名
    props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "producer.Customer03Partitioner");

    KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
    for (int i = 0; i < 10; i++) {
      producer.send(new ProducerRecord<String, String>(
              "second",
              "Key" + i,
              "Value" + i),
          new Callback() {
            public void onCompletion(RecordMetadata metadata, Exception exception) {
              // 注意看参数
              if (exception == null) {
                System.out.println("发送成功");
                System.out.println(metadata.partition() + "--" + metadata.offset());
              } else {
                System.out.println("发送失败");
              }
            }
          });
//      Thread.sleep(2000);
    }
    /**
     *  发送到指定的分区 0
     * 0--2
     * 发送成功
     * 0--3
     * 发送成功
     * 0--4
     * 发送成功
     * 0--5
     * 发送成功
     * 0--6
     * 发送成功
     * 0--7
     * 发送成功
     * 0--8
     * 发送成功
     * 0--9
     * 发送成功
     * 0--10
     * 发送成功
     * 0--11
     */

    // 关闭资源
    producer.close();
  }
}
