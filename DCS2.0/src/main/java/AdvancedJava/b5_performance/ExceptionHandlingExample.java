package AdvancedJava.b5_performance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ExceptionHandlingExample {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlingExample.class);

    public static void main(String[] args) {
        try {
            rethrowExample();
            wrapExample();
            interruptRestoreExample();
            loggingExample();
        } catch (Exception e) {
            logger.error("Ana metotta hata yakalandı: {}", e.getMessage(), e);
        }
    }

    // 1. Rethrow: Hata üst katmana iletme
    public static void rethrowExample() throws IOException {
        try {
            throw new IOException("Rethrow hatası");
        } catch (IOException e) {
            logger.error("Rethrow hatası yakalandı", e);
            throw e; // ✅ Hata üst katmana iletir
        }
    }

    // 2. Wrap: Hata sarmalayarak iletme
    public static void wrapExample() {
        try {
            throw new IOException("Wrap hatası");
        } catch (IOException e) {
            logger.error("Wrap hatası yakalandı", e);
            throw new RuntimeException("Wrap hatası sarmalandı", e); // Unchecked exception ile iletir
        }
    }

    // 3. Interrupt Restore: Thread kesilmesi durumu
    public static void interruptRestoreExample() {
        try {
            Thread.sleep(100); // Simüle edilen InterruptedException
            throw new InterruptedException("Interrupt hatası");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); //  Kesilme durumu restore edilir
            logger.error("Interrupt hatası yakalandı", e);
            throw new RuntimeException("Interrupt hatası sarmalandı", e);
        }
    }

    // 4. Logging Framework: Hata loglama
    public static void loggingExample() {
        try {
            throw new IOException("Logging hatası");
        } catch (IOException e) {
            logger.error("Logging hatası yakalandı", e); //  Hata loglanır
            throw new RuntimeException("Logging hatası sarmalandı", e);
        }
    }
}

