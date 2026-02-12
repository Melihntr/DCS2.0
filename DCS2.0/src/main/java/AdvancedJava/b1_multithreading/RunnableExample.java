package AdvancedJava.b1_multithreading;

public class RunnableExample {
    public static void main(String[] args) {
        // Runnable'ı implement eden bir sınıf
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable çalışıyor - " + Thread.currentThread().getName());
            }
        };

        // Thread oluşturma ve başlatma
        Thread thread = new Thread(runnable);
        Thread thread1 = new Thread(runnable);


        thread.start();
        thread1.start();

        // Main thread'de çalışan kod
        System.out.println("Main thread çalışıyor - " + Thread.currentThread().getName());
    }
}
