package CodeSmellsAndRefactoring;

import java.util.List;

/**
 * 4.3: Refactoring Teknikleri
 * I. Extract Method: Metodu dışarı çıkarma.
 * II. Preserve Whole Object: Nesnenin tamamını gönderme.
 */
public class RefactoringTechniques {

    class Item { double price; }
    class Order { List<Item> items; String customerName; }

    // KOKU: Long Method & Primitive Obsession
    public void printOrderDetails(Order order) {
        // Hesaplama mantığı metodun içinde boğulmuş.
        double total = 0;
        for (Item i : order.items) total += i.price;

        System.out.println("Customer: " + order.customerName);
        System.out.println("Total: " + total);
    }

    // İYİ (Refactored): Extract Method
    public void printOrderDetailsClean(Order order) {
        double total = calculateTotal(order.items); // Hesaplama ayrıldı.
        printSummary(order.customerName, total);    // Yazdırma ayrıldı.
    }

    private double calculateTotal(List<Item> items) {
        return items.stream().mapToDouble(i -> i.price).sum();
    }

    private void printSummary(String name, double total) {
        System.out.println("Customer: " + name);
        System.out.println("Total: " + total);
    }
}