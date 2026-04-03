package CleanCode;

/**
 * C1.5: Yorum Satırları (Comments)
 * Amaç: Kodun yorumsuz anlaşılmasını sağlamak ve 'Zombie' kodlardan kurtulmak.
 */
public class CommentCleaner {

    // KÖTÜ: Kod kendini anlatmadığı için yorum gereksinimi ve ölü kodlar.
    public void processBad() {
        // Kullanıcı aktif mi kontrol et (Kod kendini anlatmıyor)
        // if (u.st == 1 && u.age > 18) { ... }  <-- ZOMBIE KOD
        
        int s = 1; // Durum aktif
        if (s == 1) {
            System.out.println("İşlem yapılıyor...");
        }
    }

    // İYİ: Metot ve değişken isimleri yorum ihtiyacını ortadan kaldırır.
    public void processGood() {
        boolean isAccountActive = true;
        
        if (isAccountActive) {
            System.out.println("Temiz kod: Yorum satırına ihtiyaç yok.");
        }
    }

    public static void main(String[] args) {
        new CommentCleaner().processGood();
    }
}
