package AdvancedJava.b2_concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrencyIssues {

    // Deadlock Example
    static class DeadlockExample {
        private final Lock lock1 = new ReentrantLock();
        private final Lock lock2 = new ReentrantLock();

        void deadlock() {
            Thread thread1 = new Thread(() -> {
                lock1.lock();
                try {
                    System.out.println("Thread 1 has Lock 1. Waiting for Lock 2...");
                    lock2.lock();
                    try {
                        System.out.println("Thread 1 acquired both locks.");
                    } finally {
                        lock2.unlock();
                    }
                } finally {
                    lock1.unlock();
                }
            });

            Thread thread2 = new Thread(() -> {
                lock2.lock();
                try {
                    System.out.println("Thread 2 has Lock 2. Waiting for Lock 1...");
                    lock1.lock();
                    try {
                        System.out.println("Thread 2 acquired both locks.");
                    } finally {
                        lock1.unlock();
                    }
                } finally {
                    lock2.unlock();
                }
            });

            thread1.start();
            thread2.start();
        }
    }

    // Livelock Example
    static class LivelockExample {
        private final Lock lock = new ReentrantLock();
        private boolean condition = false;

        void livelock() {
            Thread thread1 = new Thread(() -> {
                while (!condition) {
                    if (lock.tryLock()) {
                        try {
                            System.out.println("Thread 1 acquired the lock.");
                            condition = true;
                        } finally {
                            lock.unlock();
                        }
                    } else {
                        System.out.println("Thread 1 could not acquire the lock. Retrying...");
                    }
                }
            });

            Thread thread2 = new Thread(() -> {
                while (!condition) {
                    if (lock.tryLock()) {
                        try {
                            System.out.println("Thread 2 acquired the lock.");
                            condition = true;
                        } finally {
                            lock.unlock();
                        }
                    } else {
                        System.out.println("Thread 2 could not acquire the lock. Retrying...");
                    }
                }
            });

            thread1.start();
            thread2.start();
        }
    }

    // Starvation Example
    static class StarvationExample {
        private final Lock lock = new ReentrantLock();
        private volatile boolean highPriorityTaskRunning = false;

        void starvation() {
            Thread highPriorityThread = new Thread(() -> {
                while (true) {
                    lock.lock();
                    try {
                        highPriorityTaskRunning = true;
                        System.out.println("High-priority thread is running.");
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                        highPriorityTaskRunning = false;
                    }
                }
            });

            Thread lowPriorityThread = new Thread(() -> {
                while (true) {
                    while (highPriorityTaskRunning) {
                        System.out.println("Low-priority thread is waiting.");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    lock.lock();
                    try {
                        System.out.println("Low-priority thread is running.");
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            });

            highPriorityThread.setPriority(Thread.MAX_PRIORITY);
            lowPriorityThread.setPriority(Thread.MIN_PRIORITY);

            highPriorityThread.start();
            lowPriorityThread.start();
        }
    }

    public static void main(String[] args) {
        // Uncomment each section to test the respective issue

        // Test Deadlock
        //new DeadlockExample().deadlock();

        // Test Livelock
        //new LivelockExample().livelock();

        // Test Starvation
        //new StarvationExample().starvation();
    }
}
