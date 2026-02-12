package AdvancedJava.b2_concurrency;

public class ThreadLocalExample {
    // ThreadLocal değişken
    private static final ThreadLocal<Integer> threadLocalCounter = new ThreadLocal<>();

    public static void main(String[] args) {
        // Thread'ları başlat
        Thread thread1 = new Thread(() -> {
            // Her thread için ThreadLocal değişkeni ayarla
            threadLocalCounter.set(0);
            for (int i = 0; i < 5; i++) {
                threadLocalCounter.set(threadLocalCounter.get() + 1);
                System.out.println("Thread 1: " + threadLocalCounter.get());
            }
        });

        Thread thread2 = new Thread(() -> {
            threadLocalCounter.set(0);
            for (int i = 0; i < 5; i++) {
                threadLocalCounter.set(threadLocalCounter.get() + 1);
                System.out.println("Thread 2: " + threadLocalCounter.get());
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

        System.out.println("ThreadLocal örneği tamamlandı.");
    }
}

