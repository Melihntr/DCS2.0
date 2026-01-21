package CoreJava.a2_language_basics.operators;

public class Ternary {
    public static void main(String[] args) {
        int sayi = 10;
        String sonuc = (sayi % 2 == 0) ? "Çift" : "Tek";
        System.out.println(sonuc); // Output: Çift
    }
}
