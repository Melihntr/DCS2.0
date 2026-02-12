package AdvancedJava.b3_async_programming;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureExample {
    public static void main(String[] args) {
        // Asenkron olarak bir sayıya karekök almak
        CompletableFuture<Double> future = CompletableFuture.supplyAsync(() -> {
            int number = 25;
            return Math.sqrt(number);
        });

        // Future'ın sonucunu almak
        try {
            Double result = future.get();
            System.out.println("Karekök: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}