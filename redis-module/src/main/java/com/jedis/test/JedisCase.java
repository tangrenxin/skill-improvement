package com.jedis.test;

import java.util.Random;
import redis.clients.jedis.Jedis;

/**
 * @Description:
 * 8.1.完成一个手机验证码功能
 * 要求：
 * 1、输入手机号，点击发送后随机生成6位数字码，2分钟有效
 * 2、输入验证码，点击验证，返回成功或失败
 * 3、每个手机号每天只能输入3次
 * @Author: tangrenxin
 * @Date: 2021/12/5 下午9:09
 */
public class JedisCase {

  public static void main(String[] args) {
    //模拟验证码发送
//    verifyCode("17308598048");

    // 验证码 校验
    getRedisCode("17308598048", "793622");

  }

  //3 验证码校验
  public static void getRedisCode(String phone, String code) {
    //从redis获取验证码
    Jedis jedis = new Jedis("127.0.0.1", 6379);
    //验证码key
    String codeKey = "VerifyCode" + phone + ":code";
    String redisCode = jedis.get(codeKey);
    //判断
    if (redisCode != null && redisCode.equals(code)) {
      System.out.println("成功");
    } else {
      System.out.println("失败");
    }
    jedis.close();
  }

  //2 每个手机每天只能发送三次，验证码放到redis中，设置过期时间120
  public static void verifyCode(String phone) {
    //连接redis
    Jedis jedis = new Jedis("127.0.0.1", 6379);

    //拼接key
    //手机发送次数key
    String countKey = "VerifyCode" + phone + ":count";
    //验证码key
    String codeKey = "VerifyCode" + phone + ":code";

    //每个手机每天只能发送三次
    String count = jedis.get(countKey);
    if (count == null) {
      //没有发送次数，第一次发送
      //设置发送次数是1
      // 一天：24*60*60
      jedis.setex(countKey, 24 * 60 * 60, "1");
    } else if (Integer.parseInt(count) <= 2) {
      //发送次数+1
      jedis.incr(countKey);
    } else if (Integer.parseInt(count) > 2) {
      //发送三次，不能再发送
      System.out.println("今天发送次数已经超过三次");
      jedis.close();
      return;
    }

    //发送验证码放到redis里面
    String vcode = getCode();
    System.out.println(vcode);
    jedis.setex(codeKey, 120, vcode);
    jedis.close();
  }

  // 1. 生成6位数字验证码
  public static String getCode() {
    Random random = new Random();
    String code = "";
    for (int i = 0; i < 6; i++) {
      int rand = random.nextInt(10);
      code += rand;
    }
    return code;
  }
}
