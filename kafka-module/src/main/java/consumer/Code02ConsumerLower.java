package consumer;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import kafka.api.FetchRequest;
import kafka.api.FetchRequestBuilder;
import kafka.cluster.BrokerEndPoint;
import kafka.javaapi.FetchResponse;
import kafka.javaapi.PartitionMetadata;
import kafka.javaapi.TopicMetadata;
import kafka.javaapi.TopicMetadataRequest;
import kafka.javaapi.TopicMetadataResponse;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.javaapi.message.ByteBufferMessageSet;
import kafka.message.Message;
import kafka.message.MessageAndOffset;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * @Description: 低级 消费者API
 * @Author: tangrenxin
 * @Date: 2021/10/24 22:21
 */
public class Code02ConsumerLower {

  /**
   * 使用低级API消费数据思路分析
   * 1.之前提到过，只要是涉及到读写，都是跟leader进行交互的
   * 2.所以在获取数据时，首先要根据topic的partition找到对应的leader，找到主副本
   * 3.获取分区最新进度：offset（这个offset就是你自己管理了，可能存mysql、hdfs、redis等）
   * 4.找到leader后，从主副本拉取分区信息
   * 5.识别主副本的变化，重试
   *    会保存leader位置信息（在哪个broker），如果leader挂了，需要更新这个列表
   *    这些信息需要消费者自己保存，offset也一样
   *
   * 根据指定的 topic,partition,offset 来获取数据
   * @param args
   */

  public static void main(String[] args) {

    // 定义相关参数
    // kafka集群
    ArrayList<String> brokers = new ArrayList<String>();
    brokers.add("localhost");

    // 端口号
    int port = 9092;
    // 主题
    String topic = "second";
    // 分区
    int partition = 0;
    // offset
    long ofsset = 2;

    Code02ConsumerLower consumerLower = new Code02ConsumerLower();
    consumerLower.getData(brokers, port, topic, partition, ofsset);

  }

  // 找分区leader
  private BrokerEndPoint findLeader(List<String> brokers, int port, String topic, int partition) {

    for (String broker : brokers) {
      // 创建获取分区 leader 的消费者对象
      SimpleConsumer getLeader = new SimpleConsumer(broker, port, 1000, 1024 * 4, "getLeader");
      // 创建一个主题元数据信息请求对象
      TopicMetadataRequest topicMetadataRequest = new TopicMetadataRequest(
          Collections.singletonList(topic));
      // 获取主题元数据返回值
      TopicMetadataResponse metadataResponse = getLeader.send(topicMetadataRequest);
      // 解析元数据返回值
      List<TopicMetadata> topicMetadata = metadataResponse.topicsMetadata();
      // 遍历主题元数据
      for (TopicMetadata topicMetadatum : topicMetadata) {
        List<PartitionMetadata> partitionMetadata = topicMetadatum.partitionsMetadata();
        // 遍历分区
        for (PartitionMetadata partitionMetadatum : partitionMetadata) {
          if (partitionMetadatum.partitionId() == partition) {
            return partitionMetadatum.leader();
          }
        }
      }
    }
    return null;
  }

  // 获取数据
  private void getData(List<String> brokers, int port, String topic, int partition, long offset) {

    // 获取分区leader
    BrokerEndPoint leader = findLeader(brokers, port, topic, partition);
    if (leader == null) {
      return;
    }

    String host = leader.host();
    // TODO 优化：获取保存起来的 offset

    // 创建获取数据的消费者对象
    SimpleConsumer getData = new SimpleConsumer(host, port, 1000, 1024 * 4,
        "getData");
    // 创建获取数据的对象 ，可以addFetch 多个topic
    FetchRequest fetchRequest = new FetchRequestBuilder()
        .addFetch(topic, partition, offset, 100000).build();
    // 获取数据返回值
    FetchResponse fetchResponse = getData.fetch(fetchRequest);
    // 解析返回值
    ByteBufferMessageSet messageAndOffsets = fetchResponse.messageSet(topic, partition);
    for (MessageAndOffset messageAndOffset : messageAndOffsets) {
      long offset1 = messageAndOffset.offset();
      // TODO 优化：保存 offset mysql、redis等
      Message message = messageAndOffset.message();
      ByteBuffer payload = message.payload();
      byte[] bytes = new byte[payload.limit()];
      payload.get(bytes);

      // 对每条数据进行打印
      System.out.println(offset1 + "--" + new String(bytes));
    }

  }


}
