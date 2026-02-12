package AdvancedJava.b1_multithreading;

public class DaemonThreadExample {
    public static void main(String[] args) {
        // Daemon thread oluşturma
        Thread daemonThread = new Thread(() -> {
            int count = 0;
            while (true) { // Sonsuz döngü
                System.out.println("Daemon thread çalışıyor - Sayı: " + count);
                count++;
                try {
                    Thread.sleep(1000); // 1 saniye bekle
                } catch (InterruptedException e) {
                    System.out.println("Daemon thread sonlandırılıyor...");
                    break;
                }
            }
        });

        // Thread'i daemon olarak ayarla
        daemonThread.setDaemon(true);

        // Thread'i başlat
        daemonThread.start();

        // Main thread'de çalışan kod
        System.out.println("Main thread çalışıyor...");
        try {
            Thread.sleep(12000); // Main thread 3 saniye boyunca çalışır
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main thread sonlandırılıyor...");
    }
}
