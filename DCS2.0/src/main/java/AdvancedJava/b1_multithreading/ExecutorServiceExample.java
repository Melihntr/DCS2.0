package AdvancedJava.b1_multithreading;

import java.util.concurrent.*;

public class ExecutorServiceExample {
    public static void main(String[] args) {
        try {
            // 1. ThreadPoolExecutor
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                    2, // Minimum thread count
                    4, // Maximum thread count
                    5, // Keep alive time
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>() // Task queue
            );

            threadPoolExecutor.execute(() -> {
                System.out.println("ThreadPoolExecutor Task 1");
            });

            threadPoolExecutor.execute(() -> {
                System.out.println("ThreadPoolExecutor Task 2");
            });

            // 2. FixedThreadPool
            ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

            fixedThreadPool.execute(() -> {
                System.out.println("FixedThreadPool Task 1");
            });

            fixedThreadPool.execute(() -> {
                System.out.println("FixedThreadPool Task 2");
            });

            fixedThreadPool.execute(() -> {
                System.out.println("FixedThreadPool Task 3");
            });

            // 3. CachedThreadPool
            ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

            cachedThreadPool.execute(() -> {
                System.out.println("CachedThreadPool Task 1");
            });

            cachedThreadPool.execute(() -> {
                System.out.println("CachedThreadPool Task 2");
            });

            // 4. SingleThreadExecutor
            ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

            singleThreadExecutor.execute(() -> {
                System.out.println("SingleThreadExecutor Task 1");
            });

            singleThreadExecutor.execute(() -> {
                System.out.println("SingleThreadExecutor Task 2");
            });

            // 5. ScheduledThreadPool
            ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);

            scheduledThreadPool.scheduleAtFixedRate(() -> {
                System.out.println("ScheduledThreadPool Task 1");
            }, 0, 2, TimeUnit.SECONDS);

            scheduledThreadPool.scheduleWithFixedDelay(() -> {
                System.out.println("ScheduledThreadPool Task 2");
            }, 0, 1, TimeUnit.SECONDS);

            // Shutdown all executors
            threadPoolExecutor.shutdown();
            fixedThreadPool.shutdown();
            cachedThreadPool.shutdown();
            singleThreadExecutor.shutdown();
            scheduledThreadPool.shutdown();

            // Wait for all tasks to complete
            threadPoolExecutor.awaitTermination(1, TimeUnit.MINUTES);
            fixedThreadPool.awaitTermination(1, TimeUnit.MINUTES);
            cachedThreadPool.awaitTermination(1, TimeUnit.MINUTES);
            singleThreadExecutor.awaitTermination(1, TimeUnit.MINUTES);
            scheduledThreadPool.awaitTermination(1, TimeUnit.MINUTES);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
