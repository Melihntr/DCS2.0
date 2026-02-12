package AdvancedJava.b1_multithreading;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableExample {
    public static void main(String[] args) {
        // ExecutorService oluşturma
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Callable'ı implement eden bir sınıf
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() {
                System.out.println("Callable çalışıyor - " + Thread.currentThread().getName());
                return 42; // Bir sonuç döndürüyoruz
            }
        };

        // Callable'ı executor'a gönderme
        Future<Integer> future = executor.submit(callable);

        try {
            // Sonucu alıyoruz
            System.out.println("Callable'dan dönen sonuç: " + future.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Executor'ı kapatma
        executor.shutdown();
    }
}
