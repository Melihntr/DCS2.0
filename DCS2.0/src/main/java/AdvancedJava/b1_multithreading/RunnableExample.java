package AdvancedJava;

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
        thread.start();

        // Main thread'de çalışan kod
        System.out.println("Main thread çalışıyor - " + Thread.currentThread().getName());
    }
}
