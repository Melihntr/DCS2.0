package AdvancedJava.b1_multithreading;

import java.util.concurrent.Executors;


public class VirtualThreadExample {

    public static void main(String[] args) throws InterruptedException {

        // Use try-with-resources to ensure the executor is closed automatically
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(() -> {
                System.out.println("Task executed by a virtual thread");
            });
        }

        Thread vThread = Thread.startVirtualThread(() -> {
            System.out.println("Virtual thread başladı");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Virtual thread bitti");
        });

        vThread.join();

        System.out.println("Main thread bitti");
    }
}
