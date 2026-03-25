package CleanCode;

/**
 * C1.6: Fonksiyonlar ve DRY
 * Amaç: Kod tekrarını önlemek (Don't Repeat Yourself) ve fonksiyonu tek işe odaklamak.
 */
public class FunctionMastery {

    // KÖTÜ: Kod tekrarı ve çok iş yapan fonksiyon.
    public void greetBad() {
        System.out.println("Merhaba Ahmet");
        System.out.println("Merhaba Mehmet");
        System.out.println("Merhaba Ayşe");
    }

    // İYİ: DRY prensibi ve tek sorumluluk.
    public void greetUser(String name) {
        // Fonksiyon sadece selam verme işini yapar.
        if (name == null || name.isEmpty()) return;
        System.out.println("Merhaba " + name);
    }

    public void greetAll() {
        String[] users = {"Ahmet", "Mehmet", "Ayşe"};
        for (String user : users) {
            greetUser(user); // Tekrar eden mantık metodlaştırıldı.
        }
    }

    public static void main(String[] args) {
        new FunctionMastery().greetAll();
    }
}
