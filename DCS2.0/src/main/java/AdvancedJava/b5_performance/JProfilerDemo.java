package AdvancedJava.b5_performance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JProfilerDemo {

    // Memory leak simülasyonu için static liste
    private static final List<byte[]> memoryLeakList = new ArrayList<>();

    // Lock objesi
    private static final Object lock = new Object();

    public static void main(String[] args) {

        // CPU yoğun thread
        Thread cpuThread = new Thread(() -> {
            while (true) {
                intensiveCalculation();
            }
        });

        // Memory leak thread
        Thread memoryThread = new Thread(() -> {
            while (true) {
                createObjects();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Lock contention thread
        Thread lockThread1 = new Thread(() -> {
            while (true) {
                synchronizedMethod();
            }
        });

        Thread lockThread2 = new Thread(() -> {
            while (true) {
                synchronizedMethod();
            }
        });

        cpuThread.start();
        memoryThread.start();
        lockThread1.start();
        lockThread2.start();
    }

    // CPU yoğun işlem
    public static void intensiveCalculation() {
        double result = 0;
        for (int i = 0; i < 1_000_000; i++) {
            result += Math.sqrt(i) * Math.random();
        }
    }

    // Sürekli obje üretimi (Heap ve Allocation analizi için)
    public static void createObjects() {
        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            byte[] data = new byte[1024 * 100]; // 100 KB
            memoryLeakList.add(data); // GC temizleyemeyecek
        }
    }

    // Lock contention göstermek için
    public static void synchronizedMethod() {
        synchronized (lock) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
