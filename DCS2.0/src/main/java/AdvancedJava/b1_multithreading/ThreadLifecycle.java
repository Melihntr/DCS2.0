package AdvancedJava.b1_multithreading;

public class ThreadLifecycle {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println("Thread başladı.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Thread bitti.");
        });

        System.out.println("Thread henüz başlamadı.");
        thread.start();
        System.out.println("Thread başladıktan sonra.");

        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Thread tamamen bitti.");
    }
}