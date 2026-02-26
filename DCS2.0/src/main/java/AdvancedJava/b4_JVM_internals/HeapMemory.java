package AdvancedJava.b4_JVM_internals;

public class HeapMemory {
    public static void main(String[] args) {
        // Yeni bir nesne oluşturulur ve v Heap bellekte saklanır
        String metin = new String("Yeni String"); // Heap bellekte bir String nesnesi oluşturulur

        // Heap bellekteki nesneler referanslar ile erişilir
        System.out.println(metin);
    }
}
