package AdvancedJava.b3_async_programming;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AsyncErrorHandlingExample {
    public static void main(String[] args) {
        // Asenkron olarak bir sayıya karekök almak
        CompletableFuture<Double> future = CompletableFuture.supplyAsync(() -> {
            int number = -25; // Negatif bir sayı, bu da hata verecek
            return Math.sqrt(number);
        });

        // Hata oluştuğunda hata yönetimi yapmak
        future.exceptionally(e -> {
            System.out.println("Hata oluştu: " + e.getMessage());
            return null;
        });

        // Ana thread'in diğer thread'leri beklemesi
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}