package CoreJava.a5_collections;

import java.util.*;

public class SetExample {
    public static void main(String[] args) {
        // HashSet
        Set<String> hashSet = new HashSet<>();
        hashSet.add("Apple");
        hashSet.add("Banana");
        hashSet.add("Cherry");
        hashSet.add("Grapes");

        System.out.println("HashSet: " + hashSet);

        // TreeSet
        Set<String> treeSet = new TreeSet<>();
        treeSet.add("Banana");
        treeSet.add("Cherry");
        treeSet.add("Grapes");
        treeSet.add("Apple");

        System.out.println("TreeSet: " + treeSet);

        Set<String> set = new LinkedHashSet<>();
        set.add("Apple");
        set.add("Banana");
        set.add("Cherry");
        set.add("Grapes");

        System.out.println("LinkedSet: " + set);

        // Ekleme süresi karşılaştırması
        int elemanSayisi = 100000;
        Set<String> hashSetPerformance = new HashSet<>();
        Set<String> treeSetPerformance = new TreeSet<>();
        Set<String> LinkedHashSetPerformance = new LinkedHashSet<>();

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

        startTime = System.nanoTime();
        for (int i = 0; i < elemanSayisi; i++) {
            LinkedHashSetPerformance.add("Element " + i);
        }
        endTime = System.nanoTime();
        System.out.println("Linked Set ekleme süresi: " + (endTime - startTime) + " nanosaniye");

    }
}
