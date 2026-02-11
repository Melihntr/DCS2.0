package AdvancedJava.b1_multithreading;

public class threadExtends extends Thread {
    // run() metodunu override ediyoruz
    @Override
    public void run() {
        System.out.println("Thread çalışıyor - " + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        // Thread oluşturma ve başlatma
        threadExtends thread = new threadExtends();
        threadExtends thread1 = new threadExtends();

        thread.start();
        thread1.start();

        // Main thread'de çalışan kod
        System.out.println("Main thread çalışıyor - " + Thread.currentThread().getName());
    }
}