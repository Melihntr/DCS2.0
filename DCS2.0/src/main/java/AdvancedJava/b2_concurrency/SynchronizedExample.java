package AdvancedJava.b2_concurrency;

public class SynchronizedExample {
    // Paylaşılan bir değişken
    private int counter = 0;

    // Synchronized metot
    public synchronized void incrementCounter() {
        counter++;
        System.out.println("Thread " + Thread.currentThread().getName() + " counter: " + counter);
    }

    public static void main(String[] args) {
        SynchronizedExample example = new SynchronizedExample();

        // İki thread oluştur
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                example.incrementCounter();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                example.incrementCounter();
            }
        });

        // Thread'ları başlat
        thread1.start();
        thread2.start();

        try {
            // Ana thread'in diğer thread'leri beklemesi
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Synchronized örneği tamamlandı.");
    }
}
