package design.patterns.a.creation.prototype.pattern.clone.test;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/8/26 15:28
 */
public class Test {

  public static void main(String[] args) {
    Student student1 = new Student();
    student1.setName("Kevin");
    student1.setGender("Male");
    System.out.println("student1" + student1);
    System.out.println("student1Hashcode" + student1.hashCode());


    try{
      Student student2 = student1.clone();
      System.out.println("Clone student2 from student1...");
      System.out.println("student2"+student2);
      System.out.println("student2Hashcode" + student2.hashCode());
      System.out.println(student1.equals(student2));
      System.out.println("Alter student2...");
      student2.setName("Alice");
      student2.setGender("Female");
      System.out.println("student1"+student1);
      System.out.println("student2"+student2);
    }catch(CloneNotSupportedException e){
      e.printStackTrace();
    }


  }

}
