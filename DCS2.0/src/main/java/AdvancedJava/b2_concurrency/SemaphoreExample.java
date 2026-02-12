package AdvancedJava.b2_concurrency;

import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    private static Semaphore semaphore = new Semaphore(3); // 3 connection allowed
    private static int connectionCount = 0;

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                try {
                    semaphore.acquire();
                    connectionCount++;
                    System.out.println("Connection established: " + connectionCount);
                    Thread.sleep(1000);
                    connectionCount--;
                    System.out.println("Connection released: " + connectionCount);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            });
            thread.start();
        }
    }
}
