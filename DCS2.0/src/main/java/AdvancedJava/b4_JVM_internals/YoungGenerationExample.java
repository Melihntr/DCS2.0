package AdvancedJava.b4_JVM_internals;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

public class YoungGenerationExample {
    public static void main(String[] args) {
        // Eden Space'te oluşturulan bir nesne
        byte[]edenArray = new byte[1024]; // Küçük bir nesne, Eden'de oluşturulur

        // Nesnelerin yaşam döngüsünü simüle etmek için Minor GC'yi zorunlu tut
        System.gc(); // Minor GC'yi manuel olarak tetikle

        // Survivor Space'te kalan bir nesne
        Object survivor = new Object();

        // Nesnelerin yaşam döngüsünü takip etmek için finalize() metodunu kullan
        try {
            survivor.finalize();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        // Nesnelerin Young Generation'dan Old Generation'a geçişini simüle et
        makeOld(survivor);

        // Old Generation'da kalan bir nesne
        byte[]oldArray = new byte[1024 * 1024]; // Büyük bir nesne, Old Gen'e gidebilir

        System.out.println("Young Generation ve Survivor Space örneği tamamlandı.");
    }

    // Nesnelerin yaşlanmasını simüle eden bir metod
    private static void makeOld(Object obj) {
        // Nesnenin yaşını artırmak için referansını koru
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        PhantomReference<Object> ref = new PhantomReference<>(obj, queue);

        // Nesnenin referansının kalmamasını sağla
        obj = null;

        // Nesnenin temizlenmesini bekle
        try {
            queue.remove();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
