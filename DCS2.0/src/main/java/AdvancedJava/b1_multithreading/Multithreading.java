package AdvancedJava.b1_multithreading;

public class Multithreading {
    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> { //runnable interface'ini lambda ifadesiyle implement ediyoruz
            for (int i = 0; i < 10; i++) {
                System.out.println("Thread 1: " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("Thread 2: " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }
    }
}
