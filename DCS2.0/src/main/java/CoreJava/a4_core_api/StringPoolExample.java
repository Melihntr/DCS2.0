package CoreJava.a4_core_api;

public class StringPoolExample {
    public static void main(String[] args) {
        String str1 = "Hello";
        String str2 = "Hello";
        String str3 = new String("Hello");

        System.out.println(str1 == str2); // Output: true
        System.out.println(str1 == str3); // Output: false

        System.out.println(str1.equals(str3)); // Output: true
    }
}