package CoreJava.a5_collections;

import java.util.*;

public class SetExample {
    public static void main(String[] args) {
        // HashSet
        Set<String> hashSet = new HashSet<>();
        hashSet.add("Apple");
        hashSet.add("Banana");
        hashSet.add("Cherry");

        System.out.println("HashSet: " + hashSet);

        // TreeSet
        Set<String> treeSet = new TreeSet<>();
        treeSet.add("Apple");
        treeSet.add("Banana");
        treeSet.add("Cherry");

        System.out.println("TreeSet: " + treeSet);

        // Ekleme süresi karşılaştırması
        int elemanSayisi = 100000;
        Set<String> hashSetPerformance = new HashSet<>();
        Set<String> treeSetPerformance = new TreeSet<>();

        long startTime = System.nanoTime();
        for (int i = 0; i < elemanSayisi; i++) {
            hashSetPerformance.add("Element " + i);
        }
        long endTime = System.nanoTime();
        System.out.println("HashSet ekleme süresi: " + (endTime - startTime) + " nanosaniye");

        startTime = System.nanoTime();
        for (int i = 0; i < elemanSayisi; i++) {
            treeSetPerformance.add("Element " + i);
        }
        endTime = System.nanoTime();
        System.out.println("TreeSet ekleme süresi: " + (endTime - startTime) + " nanosaniye");
    }
}
