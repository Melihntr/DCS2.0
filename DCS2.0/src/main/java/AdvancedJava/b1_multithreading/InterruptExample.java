package AdvancedJava.b1_multithreading;

public class InterruptExample {
    public static void main(String[] args) {
        // Yeni bir thread oluştur
        Thread thread = new Thread(() -> {
            try {
                // Thread'in duraklayabileceği bir noktada duraklatma isteği gönder
                System.out.println("Thread duraklatılıyor...");
                Thread.sleep(1000);
                System.out.println("Thread devam etti.");
            } catch (InterruptedException e) {
                // Thread duraklatılmışsa bu blok çalışır
                System.out.println("Thread duraklatılmıştır.");
            }
        });

        // Thread'i başlat
        thread.start();

        // Biraz beklet
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Thread'i duraklat
        thread.interrupt();

    }
}
