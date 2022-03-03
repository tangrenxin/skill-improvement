package a.array;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2022/1/17 23:25
 */
public class ClickHouseTest {
  private static final String URL = "jdbc:clickhouse://192.168.0.103:8123/default";
  private static final String USER = "default";
  private static final String PASSWORD = "123456";
  public static void main(String[] args) {
    Connection connection = null;
    Statement statement = null;

    try {
      // 注册JDBC驱动
      Class.forName("ru.yandex.clickhouse.ClickHouseDriver");

      // 打开连接
      connection = DriverManager.getConnection(URL, USER, PASSWORD);
      System.out.println("connected database successfully");

      // 执行查询
      statement = connection.createStatement();
      String sql = "select * from default.t_order_mt";
      ResultSet rs = statement.executeQuery(sql);

      // 从结果集中提取数据
      while (rs.next()){
        String name = rs.getString("sku_id");
        float size = rs.getFloat("total_amount");
        System.out.println(name + " " + size);
      }

      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
    }finally {
      // 释放资源
      try {
        if(statement!=null){
          statement.close();
        }
      } catch (Exception throwables) {
        throwables.printStackTrace();
      }

      try {
        if(connection!=null){
          connection.close();
        }
      } catch (Exception throwables) {
        throwables.printStackTrace();
      }
    }
  }
}
