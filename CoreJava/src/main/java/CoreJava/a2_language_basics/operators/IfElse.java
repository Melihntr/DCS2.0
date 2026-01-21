package CoreJava.a2_language_basics.operators;

public class IfElse {
    public static void main(String[] args) {
        int sayi = 10;

        if (sayi > 0) {
            System.out.println("Sayi pozitif"); // Output: Sayi pozitif
        } else if (sayi < 0) {
            System.out.println("Sayi negatif");
        } else {
            System.out.println("Sayi sifir");
        }
    }
}
