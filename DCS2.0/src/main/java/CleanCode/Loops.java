package CleanCode;

/**
 * C1.4: Döngüler (Loops)
 * Amaç: Yanlış sonuçlara yol açabilecek do-while yerine kontrollü döngüler kullanmak.
 */
public class Loops {

    public void demoLoops(int limit) {
        // ❌ KÖTÜ: do-while koşul sağlanmasa bile bloğu en az bir kez çalıştırır.
        System.out.println("--- Do-While Başlıyor ---");
        int i = 20;
        do {
            // Başlangıç değeri (20) limitten (15) büyük olsa da bu satır yazdırılır.
            System.out.println("Do-While: Koşul yanlış ama bu satır yazıldı -> " + i);
            i++;
        } while (i < limit);

        // ✅ İYİ: while döngüsü önce koşula bakar, uygun değilse bloğa hiç girmez.
        System.out.println("\n--- While Başlıyor ---");
        int j = 20;
        while (j < limit) {
            // Koşul (20 < 15) yanlış olduğu için bu blok hiç çalışmayacaktır.
            System.out.println("While: Bu satır hiç çalışmayacak (Doğru Yaklaşım) -> " + j);
            j++;
        }
    }

    public static void main(String[] args) {
        // Class ismi dosya ismiyle aynı (Loops)
        Loops app = new Loops();
        
        // Limit 15, Başlangıç 20. 
        // Beklenen: Do-while bir kez yazar, While hiç yazmaz.
        app.demoLoops(15); 
    }
}