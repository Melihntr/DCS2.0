package CleanCode;

import java.util.Arrays;
import java.util.List;

/**
 * C1.2: Metod Boyutu ve Sorumluluğu
 * Amaç: Fonksiyonların küçük parçalara bölünerek okunabilirliğin artırılması.
 */
public class MethodSizeAndResponsibility {

    // ❌ KÖTÜ YAKLAŞIM: Çok iş yapan "Dev" metod (Big Ball of Mud)
    public void handleReport(List<Integer> numbers) {
        // Hesaplama yapıyor
        double sum = 0;
        for (int n : numbers) sum += n;
        double avg = sum / numbers.size();
        
        // Loglama yapıyor
        System.out.println("İşlem zamanı: " + System.currentTimeMillis());
        
        // Yazdırma yapıyor
        System.out.println("Ortalama Skor: " + avg);
    }

    // ✅ İYİ YAKLAŞIM: Parçalanmış ve odaklanmış metodlar
    public void generateReport(List<Integer> numbers) {
        double average = calculateAverage(numbers);
        printReport(average);
    }

    private double calculateAverage(List<Integer> numbers) {
        // Her fonksiyon tek bir işi en iyi şekilde yapmalı
        return numbers.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

    private void printReport(double result) {
        System.out.println("Rapor Sonucu: " + result);
    }

    public static void main(String[] args) {
        MethodSizeAndResponsibility example = new MethodSizeAndResponsibility();
        example.generateReport(Arrays.asList(10, 20, 30, 40));
    }
}
