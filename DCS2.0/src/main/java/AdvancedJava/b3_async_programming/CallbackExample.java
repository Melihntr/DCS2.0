package AdvancedJava.b3_async_programming;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CallbackExample {
    public static void main(String[] args) {
        // Asenkron olarak bir sayıya karekök almak
        CompletableFuture<Double> future = CompletableFuture.supplyAsync(() -> {
            int number = 25;
            return Math.sqrt(number);
        });

        // Sonuç alındığında callback tetiklenir
        future.thenAccept(result -> {
            System.out.println("Karekök: " + result);
        });

        // Ana thread'in diğer thread'leri beklemesi
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
