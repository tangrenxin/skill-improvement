package c.sort.a.easy;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @Description:
 * 720. 词典中最长的单词
 *
 * 给出一个字符串数组words组成的一本英语词典。从中找出最长的一个单词，
 * 该单词是由words词典中其他单词逐步添加一个字母组成。
 * 若其中有多个可行的答案，则返回答案中字典序最小的单词。
 *
 * 若无答案，则返回空字符串。
 *
 * 示例 1：
 *
 * 输入：
 * words = ["w","wo","wor","worl", "world"]
 * 输出："world"
 * 解释：
 * 单词"world"可由"w", "wo", "wor", 和 "worl"添加一个字母组成。
 *
 * 示例 2：
 *
 * 输入：
 * words = ["a", "banana", "app", "appl", "ap", "apply", "apple"]
 * 输出："apple"
 * 解释：
 * "apply"和"apple"都能由词典中的单词组成。但是"apple"的字典序小于"apply"。
 *
 * 提示：
 *
 *     所有输入的字符串都只包含小写字母。
 *     words数组长度范围为[1,1000]。
 *     words[i]的长度范围为[1,30]。
 *
 *     这题有点不严谨啊。 没说要从一个字母开始，也没说只在上一个单词最后加一个字母。 读完题没头绪看看评论都在搞前缀啥的，
 *     我寻思着前一个单词的位置里随便加一个字母，整前缀有啥用，然后自己试了试["w","ow"]，果然答案是“w“，我直接
 * @Author: tangrenxin
 * @Date: 2021/9/30 17:29
 */
public class LeetCode720LongestWord {

  public static void main(String[] args) {
//    String[] words = {"a", "banana", "app", "appl", "ap", "apply", "apple"};
//    String[] words = {"b","br","bre","brea","break","breakf","breakfa","breakfas","breakfast", "l","lu","lun","lunc","lunch","d","di","din","dinn","dinne","dinner"};
    String[] words = {"yo", "ew", "fc", "zrc", "yodn", "fcm", "qm", "qmo", "fcmz", "z", "ewq", "yod", "ewqz", "y"};
//    String[] words = {"m","mo","moc","moch","mocha","l","la","lat","latt","latte","c","ca","cat"};
    String res2 = longestWord2(words);
    System.out.println("====");
    System.out.println(res2);
  }

  public static String longestWord2(String[] words) {
    Arrays.sort(words);
    int maxSize = 0;
    String wordRes = "";
    for (int i = 0; i < words.length; i++) {
      String curr = words[i];
      System.out.println(curr);
      if (i + 1 < words.length && words[i + 1].contains(curr)) {
        if (words[i + 1].length() > maxSize) {
          wordRes = words[i + 1];
          maxSize = wordRes.length();
        } else if(words[i + 1].length() == maxSize){
          if(words[i + 1].compareTo(wordRes) == 0){
            wordRes = words[i + 1];
          }
        }
      }
    }
    return wordRes;
  }
}
