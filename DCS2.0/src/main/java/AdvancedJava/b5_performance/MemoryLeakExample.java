package AdvancedJava.b5_performance;

public class MemoryLeakExample {
    public static void main(String[] args) {
        while (true) {
            // Bellek sızıntısı yaratmak için nesneleri tutmak
            byte[] memoryLeak = new byte[1024 * 1024]; // 1 MB bellek kullanır
        }
    }
}
