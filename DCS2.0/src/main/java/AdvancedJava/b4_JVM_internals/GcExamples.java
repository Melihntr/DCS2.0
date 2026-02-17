package AdvancedJava.b4_JVM_internals;

public class GcExamples {
    public static void main(String[] args) {
        // CMS GC'yi zorunlu tut
        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");

        // Büyük bir nesne oluşturup hafızadan temizle
        byte[] array = new byte[1024 * 1024]; // 1 MB'lık bir array

        // Nesnenin temizlenmesini zorunlu tut
        array = null;
        System.gc();

        System.out.println("Garbage Collector örneği tamamlandı.");
    }
    /*
    java -XX:+UseSerialGC GcExamples
    java -XX:+UseParallelGC GcExamples
    java -XX:+UseConcMarkSweepGC GcExamples
    java -XX:+UseG1GC GcExamples
     */
}
