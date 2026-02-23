package AdvancedJava.b1_multithreading;

class ThreadLifecycle {
    private static final Object lock = new Object();
    private static final Object waitLock = new Object();

    public static void main(String[] args) throws Exception {

        Thread newThread = new Thread(() -> {});
        System.out.println("NEW state: " + newThread.getState());

        Thread runnableThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
            }
        });
        runnableThread.start();
        Thread.sleep(100);
        System.out.println("RUNNABLE state: " + runnableThread.getState());

        Thread timedWaitingThread = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ignored) {}
        });
        timedWaitingThread.start();
        Thread.sleep(100);
        System.out.println("TIMED_WAITING state: " + timedWaitingThread.getState());

        Thread waitingThread = new Thread(() -> {
            synchronized (waitLock) {
                try {
                    waitLock.wait();
                } catch (InterruptedException ignored) {}
            }
        });
        waitingThread.start();
        Thread.sleep(100);
        System.out.println("WAITING state: " + waitingThread.getState());

        Thread lockHolder = new Thread(() -> {
            synchronized (lock) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ignored) {}
            }
        });

        Thread blockedThread = new Thread(() -> {
            synchronized (lock) {
            }
        });

        lockHolder.start();
        Thread.sleep(100);
        blockedThread.start();
        Thread.sleep(100);
        System.out.println("BLOCKED state: " + blockedThread.getState());

        runnableThread.interrupt();
        runnableThread.join();

        synchronized (waitLock) {
            waitLock.notify();
        }
        waitingThread.join();
        timedWaitingThread.join();
        lockHolder.join();
        blockedThread.join();

        Thread terminatedThread = new Thread(() -> {});
        terminatedThread.start();
        terminatedThread.join();
        System.out.println("TERMINATED state: " + terminatedThread.getState());
    }
}