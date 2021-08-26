package design.patterns.a.creation.prototype.pattern.clone.test;

/**
 * @Description:
 * @Author: tangrenxin
 * @Date: 2021/8/26 16:05
 */
public class Student implements Cloneable{

  private String name;
  private String gender;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  @Override
  public Student clone() throws CloneNotSupportedException {
    return (Student)super.clone();
  }

  @Override
  public String toString() {
    return "Student{" +
        "name='" + name + '\'' +
        ", gender='" + gender + '\'' +
        '}';
  }


}
