package AdvancedJava.b2_concurrency;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerExample {
    // Paylaşılan bir AtomicInteger değişken
    private static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {
        // İki thread oluştur
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                int value = counter.incrementAndGet();
                System.out.println("Thread 1: " + value);
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                int value = counter.incrementAndGet();
                System.out.println("Thread 2: " + value);
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

        System.out.println("Sonuç: " + counter.get());
    }
}
