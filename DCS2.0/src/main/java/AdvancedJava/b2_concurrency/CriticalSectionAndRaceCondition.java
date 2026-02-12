package AdvancedJava.b2_concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CriticalSectionAndRaceCondition {

    // Shared resource without synchronization (prone to race condition)
    static class SharedResourceWithoutLock {
        private int counter = 0;

        public void increment() {
            counter++;
            System.out.println("Incremented to: " + counter);
        }

        public void decrement() {
            counter--;
            System.out.println("Decremented to: " + counter);
        }
    }

    // Shared resource with synchronization (critical section protected by a lock)
    static class SharedResourceWithLock {
        private int counter = 0;
        private final Lock lock = new ReentrantLock();

        public void increment() {
            lock.lock();
            try {
                counter++;
                System.out.println("Incremented to: " + counter);
            } finally {
                lock.unlock();
            }
        }

        public void decrement() {
            lock.lock();
            try {
                counter--;
                System.out.println("Decremented to: " + counter);
            } finally {
                lock.unlock();
            }
        }
    }

    // Thread to increment the counter
    static class IncrementThread extends Thread {
        private final SharedResourceWithoutLock resource;

        public IncrementThread(SharedResourceWithoutLock resource) {
            this.resource = resource;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                resource.increment();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Thread to decrement the counter
    static class DecrementThread extends Thread {
        private final SharedResourceWithoutLock resource;

        public DecrementThread(SharedResourceWithoutLock resource) {
            this.resource = resource;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                resource.decrement();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Demonstrating Race Condition without Synchronization:");
        SharedResourceWithoutLock resourceWithoutLock = new SharedResourceWithoutLock();
        IncrementThread incrementThread = new IncrementThread(resourceWithoutLock);
        DecrementThread decrementThread = new DecrementThread(resourceWithoutLock);

        incrementThread.start();
        decrementThread.start();

        try {
            incrementThread.join();
            decrementThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       /* System.out.println("\nDemonstrating Critical Section with Synchronization:");
        SharedResourceWithLock resourceWithLock = new SharedResourceWithLock();
        IncrementThread incrementThreadWithLock = new IncrementThread(resourceWithLock);
        DecrementThread decrementThreadWithLock = new DecrementThread(resourceWithLock);

        incrementThreadWithLock.start();
        decrementThreadWithLock.start();

        try {
            incrementThreadWithLock.join();
            decrementThreadWithLock.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}
