package Extras;

/**
 * 5.5 & 5.6: KISS ve YAGNI Prensipleri
 */
public class KissYagniDemo {

    // ❌ İHLAL (Aşırı Mühendislik): Basit bir toplama için karmaşık yapı (KISS ihlali).
    // Ayrıca "belki lazım olur" diye eklenen generic yapılar (YAGNI ihlali).
    interface IOperation<T> { T execute(T a, T b); }
    class MathFactory { /* ... karmaşık factory kodları ... */ }

    // ✅ UYGUN (KISS & YAGNI): Problemi en basit ve gerekli yoldan çöz.
    public int sum(int a, int b) {
        return a + b; // Sadece ihtiyacın olanı yap.
    }

    public static void main(String[] args) {
        System.out.println("KISS & YAGNI: Gereksiz karmaşıklıktan kaçınıldı.");
    }
}
