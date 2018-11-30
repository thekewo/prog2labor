class Sample{
   public void display(){
      // Java Arrays with Answers
      System.out.println("Hello this is the method of the super class");
   }
}
public class MyClass extends Sample {
   public void greet(){
      System.out.println("Hello this is the method of the sub class");
   }
   public static void main(String args[]){
      MyClass obj = new MyClass();
      obj.display();
      obj.greet();
    }
}