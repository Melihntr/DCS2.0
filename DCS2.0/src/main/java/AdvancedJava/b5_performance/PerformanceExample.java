package AdvancedJava.b5_performance;

public class PerformanceExample {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        // Performans test edilecek kod
        for (int i = 0; i < 1000000; i++) {
            // İşlem
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Toplam süre: " + (endTime - startTime) + " ms");
    }
}
