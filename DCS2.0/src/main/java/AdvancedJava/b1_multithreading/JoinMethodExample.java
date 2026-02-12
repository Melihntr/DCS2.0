package AdvancedJava.b1_multithreading;

public class JoinMethodExample {
    public static void main(String[] args) {
        // Thread oluşturma
        Thread thread = new Thread(() -> {
            System.out.println("Thread başladı...");
            try {
                // Thread 2 saniye uyur
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread bitti...");
        });

        System.out.println("Main thread başladı...");

        // Thread'i başlat
        thread.start();

        try {
            // Main thread, diğer thread'in bitmesini bekler
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main thread bitti...");
    }
}
