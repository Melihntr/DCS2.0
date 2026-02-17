package AdvancedJava.b4_JVM_internals;

public class HeapMemory {
    public static void main(String[] args) {
        // Yeni bir nesne oluşturulur ve takma bellekte saklanır
        String metin = new String("Yeni String"); // Takma bellekte bir String nesnesi oluşturulur

        // Takma bellekteki nesneler referanslar ile erişilir
        System.out.println(metin);
    }
}
