//package test.wc;
//
//import org.apache.flink.api.common.functions.FlatMapFunction;
//import org.apache.flink.api.java.DataSet;
//import org.apache.flink.api.java.ExecutionEnvironment;
//import org.apache.flink.api.java.tuple.Tuple2;
//import org.apache.flink.util.Collector;
//
///**
// * @Description:
// *  批处理 WordCount
// * @Author: tangrenxin
// * @Date: 2021/10/25 23:12
// */
//public class WordCount {
//
//  public static void main(String[] args) throws Exception {
//    // 创建执行环境
//    ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
//
//    // 从文件中读取数据
//    String input = "/Users/tangrenxin/xiaomiADProject/skill-improvement/datas/word.txt";
//    DataSet<String> inputDataSet = env.readTextFile(input);
//    // 对数据集进行处理,按空格分词展开，转换成(word,1) 二元组进行统计
//    DataSet<Tuple2<String, Integer>> sumSet = inputDataSet
//        .flatMap(new MyFlatMapper())
//        .groupBy(0)
//        .sum(1);
//    sumSet.print();
////    env.execute();
//  }
//
//  // 自定义类，实现FlatMapFunction接口
//  public static class MyFlatMapper implements FlatMapFunction<String, Tuple2<String, Integer>> {
//
//    public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
//      String[] words = value.split(" ");
//      for (String word : words) {
//        out.collect(new Tuple2<String, Integer>(word, 1));
//      }
//    }
//  }
//
//}
