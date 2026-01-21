package CoreJava.a5_collections;

import java.util.*;

public class ListExample {
    public static void main(String[] args) {
        // ArrayList
        List<String> arrayList = new ArrayList<>();
        arrayList.add("Apple");
        arrayList.add("Banana");
        arrayList.add("Cherry");

        System.out.println("ArrayList: " + arrayList);

        // LinkedList
        List<String> linkedList = new LinkedList<>();
        linkedList.add("Apple");
        linkedList.add("Banana");
        linkedList.add("Cherry");

        System.out.println("LinkedList: " + linkedList);

        // Ekleme süresi karşılaştırması
        int elemanSayisi = 100000;
        List<String> arrayListPerformance = new ArrayList<>();
        List<String> linkedListPerformance = new LinkedList<>();

        long startTime = System.nanoTime();
        for (int i = 0; i < elemanSayisi; i++) {
            arrayListPerformance.add("Element " + i);
        }
        long endTime = System.nanoTime();
        System.out.println("ArrayList ekleme süresi: " + (endTime - startTime) + " nanosaniye");

        startTime = System.nanoTime();
        for (int i = 0; i < elemanSayisi; i++) {
            linkedListPerformance.add("Element " + i);
        }
        endTime = System.nanoTime();
        System.out.println("LinkedList ekleme süresi: " + (endTime - startTime) + " nanosaniye");

        // Arama süresi karşılaştırması
        startTime = System.nanoTime();
        boolean found = arrayListPerformance.contains("Element 50000");
        endTime = System.nanoTime();
        System.out.println("ArrayList arama süresi: " + (endTime - startTime) + " nanosaniye");

        startTime = System.nanoTime();
        found = linkedListPerformance.contains("Element 50000");
        endTime = System.nanoTime();
        System.out.println("LinkedList arama süresi: " + (endTime - startTime) + " nanosaniye");

        // Çıkarma süresi karşılaştırması
        startTime = System.nanoTime();
        arrayListPerformance.remove("Element 50000");
        endTime = System.nanoTime();
        System.out.println("ArrayList çıkarma süresi: " + (endTime - startTime) + " nanosaniye");

        startTime = System.nanoTime();
        linkedListPerformance.remove("Element 50000");
        endTime = System.nanoTime();
        System.out.println("LinkedList çıkarma süresi: " + (endTime - startTime) + " nanosaniye");

        // Indeksleme süresi karşılaştırması
        startTime = System.nanoTime();
        String element = arrayListPerformance.get(50000);
        endTime = System.nanoTime();
        System.out.println("ArrayList indeksleme süresi: " + (endTime - startTime) + " nanosaniye");

        startTime = System.nanoTime();
        element = linkedListPerformance.get(50000);
        endTime = System.nanoTime();
        System.out.println("LinkedList indeksleme süresi: " + (endTime - startTime) + " nanosaniye");
    }
}
