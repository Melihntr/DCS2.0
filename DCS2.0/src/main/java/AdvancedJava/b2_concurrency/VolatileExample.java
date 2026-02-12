package AdvancedJava.b2_concurrency;

public class VolatileExample {
    // Volatile değişken
    private volatile boolean flag = false;

    public static void main(String[] args) {
        VolatileExample example = new VolatileExample();
        example.runExample();
    }

    private void runExample() {
        // Volatile kullanarak çalışan thread
        Thread thread1 = new Thread(() -> {
            System.out.println("Thread 1 başladı.");
            while (!flag) {
                // Volatile değişkeni kontrol ediyor
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Thread 1 durdu.");
        });

        // Volatile kullanarak çalışan thread
        Thread thread2 = new Thread(() -> {
            System.out.println("Thread 2 başladı.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flag = true; // Volatile değişkeni ayarlıyor
            System.out.println("Thread 2 flag'i true olarak ayarladı.");
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Volatile kullanımı gösterildi.");
    }
}
