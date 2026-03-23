package CodeSmellsAndRefactoring;

/**
 * 4.4: Sürdürülebilirlik Örneği
 * Amaç: Ekip adaptasyonunu ve hata ayıklama (debugging) hızını artırmak.
 */
public class SustainabilityDemo {

    // Geliştirilmiş sürdürülebilirlik özellikleri:
    // 1. Bakım Kolaylığı: Temiz kodda hata bulmak çok daha hızlıdır.
    // 2. Geliştirme Hızı: Yeni özellik eklemek 'spagetti' kodda yol bulmaktan kolaydır.
    // 3. Ekip Adaptasyonu: Yeni gelen birisi dökümante edilmiş kodda hızla ilerler.

    public void sustainableFlow() {
        // Refactoring yapılmış bir sistemde akış şöyledir:
        // Önce kod iyileştirilir -> Testler çalıştırılır -> Yeni özellik eklenir.
        System.out.println("Sürdürülebilir bir yazılım için teknik borçlar düzenli ödenmelidir.");
    }

    public static void main(String[] args) {
        new SustainabilityDemo().sustainableFlow();
    }
}