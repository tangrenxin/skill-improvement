package com.jedis.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

/**
 * @Description:
 * jedis test
 * @Author: tangrenxin
 * @Date: 2021/12/5 下午8:40
 */
public class Demo1 {

  public static void main(String[] args) {
    // 创建Jedis对象
    Jedis jedis = new Jedis("127.0.0.1", 6379);
    String ping = jedis.ping();
    // 返回：PONG 连接成功
    System.out.println("返回值：" + ping);
    jedis.close();
  }

  /**
   * 针对 key 的操作
   */
  @Test
  public void demoKey() {
    // 创建Jedis对象
    Jedis jedis = new Jedis("127.0.0.1", 6379);
    jedis.set("k1", "v1");
    jedis.set("k2", "v2");
    jedis.set("k3", "v3");
    Set<String> keys = jedis.keys("*");
    System.out.println(keys.size());
    for (String key : keys) {
      System.out.println(key);
    }
    System.out.println(jedis.exists("k1"));
    System.out.println(jedis.ttl("k1"));
    System.out.println(jedis.get("k1"));
    jedis.close();
  }


  /**
   * String 的操作
   */
  @Test
  public void demoString() {
    // 创建Jedis对象
    Jedis jedis = new Jedis("127.0.0.1", 6379);
    jedis.set("k1", "v1");
    jedis.set("k2", "v2");
    jedis.mset("k3", "v3", "k4", "v4");
    List<String> mget = jedis.mget("k1", "k2");
    System.out.println(mget);
    jedis.close();
  }


  /**
   * list 的操作
   */
  @Test
  public void demoList() {
    // 创建Jedis对象
    Jedis jedis = new Jedis("127.0.0.1", 6379);
    jedis.lpush("list-k1","v1","v2","v3","v4","v5");
    List<String> lrange = jedis.lrange("list-k1", 0, -1);
    System.out.println(lrange);
    jedis.close();
  }

  /**
   * Set 的操作
   */
  @Test
  public void demoSet() {
    // 创建Jedis对象
    Jedis jedis = new Jedis("127.0.0.1", 6379);
    jedis.sadd("set-k1","v1","v2","v4","v4","v4");
    Set<String> smembers = jedis.smembers("set-k1");
    System.out.println(smembers);
    jedis.close();
  }

  /**
   * Hash 的操作
   */
  @Test
  public void demoHash() {
    // 创建Jedis对象
    Jedis jedis = new Jedis("127.0.0.1", 6379);
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("id","1");
    map.put("name","zhangsan");
    map.put("age","20");
    jedis.hset("hash-k1", map);
    map.put("id","2");
    map.put("name","lisi");
    map.put("age","21");
    jedis.hset("hash-k2", map);

    Map<String, String> map1 = jedis.hgetAll("hash-k1");
    Map<String, String> map2 = jedis.hgetAll("hash-k2");
    System.out.println(map1);
    System.out.println(map2);

    // 关闭连接
    jedis.close();
  }

  /**
   * Zset 的操作
   */
  @Test
  public void demoZset() {
    // 创建Jedis对象
    Jedis jedis = new Jedis("127.0.0.1", 6379);
    jedis.zadd("zset-k1",100D,"java");
    jedis.zadd("zset-k1",120D,"c++");
    jedis.zadd("zset-k1",130D,"python");
    jedis.zadd("zset-k1",90D,"php");

    Set<String> zrange = jedis.zrange("zset-k1", 0, -1);
    System.out.println(zrange);
    Set<Tuple> tuples = jedis.zrangeWithScores("zset-k1", 0, -1);
    System.out.println(tuples);

    /**
     * [php, java, c++, python]
     * [[php,90.0], [java,100.0], [c++,120.0], [python,130.0]]
     */
    // 关闭连接
    jedis.close();
  }


}
