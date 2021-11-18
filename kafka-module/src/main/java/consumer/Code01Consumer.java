package consumer;

import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * @Description: 高级消费者 API使用
 * @Author: tangrenxin
 * @Date: 2021/10/24 22:21
 */
public class Code01Consumer {

  public static void main(String[] args) {
    // 从 KafkaConsumer.java 的注释中提取到下面的simple代码，实现一个简单的消息消费过程
    // 配置信息
    Properties props = new Properties();
    // kafka 集群
    props.put("bootstrap.servers", "localhost:9092");
    // 消费者组ID
    props.put("group.id", "test");
    // 设置 自动提交 offset
    props.put("enable.auto.commit", "true");
    /**
     *  指从kafka中读取数据到提交offset过程的延时时间,
     *  假设有这样的过程：读取数据->业务处理->提交offset
     *  如果在 【业务处理】之后，服务挂了，再重启的时候，就会出现重复消费的情况
     *  所以在生产环境，我们要使用低级API来处理，即等数据完全处理完成后，再提交offset
     */
    // 设置 自动提交 offset 的时延
    props.put("auto.commit.interval.ms", "1000");
    // K/V 的反序列化
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    // 创建消费者
    KafkaConsumer<String, String> consumer = new KafkaConsumer(props);
    // 指定订阅的 topics ，一个消费者可以同时消费多个topic的数据
    consumer.subscribe(Arrays.asList("first", "second","third"));
    // 如果只消费一个topic，也可以这样写：
//    consumer.subscribe(Collections.singletonList("first"));
    while (true) {
      // consumer.poll(100); 拉取频率：100ms拉一次
      ConsumerRecords<String, String> records = consumer.poll(100);
      for (ConsumerRecord<String, String> record : records) {
        System.out.printf("topic = %s,offset = %d, key = %s, value = %s%n",
            record.topic(),
            record.offset(),
            record.key(),
            record.value());
      }
    }

    /**
     * topic = first,offset = 10, key = Key0, value = Value0
     * topic = first,offset = 12, key = Key1, value = Value1
     * topic = first,offset = 11, key = Key2, value = Value2
     * topic = first,offset = 8, key = Key3, value = Value3
     * topic = second,offset = 0, key = Key0, value = Value0
     * topic = second,offset = 1, key = Key1, value = Value1
     * topic = second,offset = 2, key = Key2, value = Value2
     * topic = second,offset = 3, key = Key3, value = Value3
     */
  }
}
