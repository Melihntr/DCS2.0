package CoreJava.a8_extras;

public class Varargs {
    void printNumbers(int... nums) {
        for (int num : nums) {
            System.out.println(num);
        }
    }
public static void main(String[] args) {
        Varargs a = new Varargs();
        a.printNumbers(1, 2, 3); // 3 parametre
        a.printNumbers(4); // 1 parametre
    }
}
