package AdvancedJava.b5_performance;

import java.util.ArrayList;
import java.util.List;

public class ProfilingExample {
    public static void main(String[] args) {
        // CPU Profiling için izlenen metot
        cpuIntensiveMethod();

        // Bellek Profiling için izlenen metot
        memoryIntensiveMethod();
    }

    private static void cpuIntensiveMethod() {
        for (int i = 0; i < 1000000; i++) {
            // CPU'yu kullanacak işlemler
        }
    }

    private static void memoryIntensiveMethod() {
        List<byte[]> memoryList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            memoryList.add(new byte[1024 * 1024]); // 1 MB bellek kullanır
        }
    }
}
