package main.java.Extras;

/**
 * 5.4: DRY Prensipleri
 * Amaç: Kod tekrarını ve tutarsızlığı önlemek.
 */
public class DryDemo {

    // İHLAL (WET): Aynı doğrulama mantığı iki farklı serviste kopyalanmış.
    class UserService {
        void register(int age) {
            if (age < 18) throw new RuntimeException("Yaş küçük"); // Tekrar 1
        }
    }
    
    class AdminService {
        void promote(int age) {
            if (age < 18) throw new RuntimeException("Yaş küçük"); // Tekrar 2
        }
    }

    // UYGUN (DRY): Mantık merkezileştirildi.
    class AgeValidator {
        public static void validate(int age) {
            if (age < 18) throw new RuntimeException("Yaş küçük");
        }
    }

    public static void main(String[] args) {
        AgeValidator.validate(20);
        System.out.println("DRY: Merkezi kontrol sağlandı.");
    }
}