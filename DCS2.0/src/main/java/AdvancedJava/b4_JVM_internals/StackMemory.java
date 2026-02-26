package AdvancedJava.b4_JVM_internals;

public class StackMemory {
    public static void main(String[] args) {
        // Yerel değişkenler stack bellekte saklanır
        int sayi = 10;
        double onSayi = 3.14;

        // stack çağrıları stack bellek üzerinde işlenir
        System.out.println("Merhaba Dünya!");
    }
}
