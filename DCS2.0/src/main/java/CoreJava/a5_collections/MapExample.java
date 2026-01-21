package CoreJava.a5_collections;

import java.util.*;

public class MapExample {
    public static void main(String[] args) {
        // HashMap
        Map<String, Integer> hashMap = new HashMap<>();
        hashMap.put("Apple", 10);
        hashMap.put("Banana", 20);
        hashMap.put("Cherry", 30);

        System.out.println("HashMap: " + hashMap);

        // TreeMap
        Map<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("Apple", 10);
        treeMap.put("Banana", 20);
        treeMap.put("Cherry", 30);

        System.out.println("TreeMap: " + treeMap);

        // Ekleme süresi karşılaştırması
        int elemanSayisi = 100000;
        Map<String, Integer> hashMapPerformance = new HashMap<>();
        Map<String, Integer> treeMapPerformance = new TreeMap<>();

        long startTime = System.nanoTime();
        for (int i = 0; i < elemanSayisi; i++) {
            hashMapPerformance.put("Key " + i, i);
        }
        long endTime = System.nanoTime();
        System.out.println("HashMap ekleme süresi: " + (endTime - startTime) + " nanosaniye");

        startTime = System.nanoTime();
        for (int i = 0; i < elemanSayisi; i++) {
            treeMapPerformance.put("Key " + i, i);
        }
        endTime = System.nanoTime();
        System.out.println("TreeMap ekleme süresi: " + (endTime - startTime) + " nanosaniye");
    }
}
