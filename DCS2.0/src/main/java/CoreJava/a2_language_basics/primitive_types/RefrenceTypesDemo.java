package CoreJava.a2_language_basics.primitive_types;

public class RefrenceTypesDemo {

    static class User{
       String name;
    }

    public static void main(String[] args){
        User u1 = new User();
        u1.name="Ali";
        User u2 = u1;
        u2.name="Ahmet";
        System.out.println(u1.name);

    }
}
