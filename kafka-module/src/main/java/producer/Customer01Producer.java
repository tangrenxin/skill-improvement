package producer;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

public class Customer01Producer {

  /**
   * 生产者的基本使用
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

    // 这么多配置记不住怎么办? 进入 ProducerConfig 查看源码，可以看到所有的 配置项及其解释
    //  ProducerConfig
    // 不想写 "acks" 等，还可以使用这种方式：
    // props.put(ProducerConfig.ACKS_CONFIG, "all");

    KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
    for (int i = 0; i < 100; i++) {
      producer.send(new ProducerRecord<String, String>(
          "second",
          "Key"+i,
          "Value"+i));
      Thread.sleep(2000);
    }

    producer.close();
  }
}
