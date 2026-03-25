package main.java.CleanCode;
/**
 * C1.7: Hata Yönetimi (Error Handling)
 * Amaç: Program akışını bozmadan hataları güvenli bir kapsamda (scope) ele almak.
 */
public class ErrorHandling {

    // KÖTÜ: Hata durumunda sadece null veya eksi değer dönmek (Belirsizlik yaratır).
    public int divideBad(int a, int b) {
        if (b == 0) return -1; // -1 gerçek bir sonuç mu yoksa hata mı?
        return a / b;
    }

    // İYİ: Try-Catch kullanarak hatayı yönetmek ve programı tutarlı tutmak.
    public void divideGood(int a, int b) {
        try {
            // Uygulama bu blokta hata olabileceğini bilir.
            int result = a / b;
            System.out.println("Sonuç: " + result);
        } catch (ArithmeticException e) {
            // Catch bloğu programı tutarlı bir durumda bırakır.
            System.err.println("Hata: Sıfıra bölme işlemi yapılamaz! " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ErrorHandling app = new ErrorHandling();
        app.divideGood(10, 0);
    }
}