package CoreJava.a2_language_basics.primitive_types;

public class PassByValueDemo {
    public static void main(String[] args) {
        // String kullanımı
        String str = "Merhaba";
        System.out.println("String: " + str); // Output: Merhaba

        // String'i manipüle etme
        long startTimeString = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            str += " yeni"; // Her seferinde yeni bir String nesnesi oluşturulur
        }
        long endTimeString = System.currentTimeMillis();
        System.out.println("String Manipülasyonu Süresi: " + (endTimeString - startTimeString) + " ms");

        // StringBuffer kullanımı
        StringBuffer sb = new StringBuffer("Merhaba");
        System.out.println("StringBuffer: " + sb); // Output: Merhaba

        // StringBuffer'i manipüle etme
        long startTimeSB = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            sb.append(" yeni"); // Orijinal nesne güncellendi
        }
        long endTimeSB = System.currentTimeMillis();
        System.out.println("StringBuffer Manipülasyonu Süresi: " + (endTimeSB - startTimeSB) + " ms");

        // Sonuçları karşılaştır
        System.out.println("\nSonuçlar:");
        System.out.println("String Manipülasyonu Süresi: " + (endTimeString - startTimeString) + " ms");
        System.out.println("StringBuffer Manipülasyonu Süresi: " + (endTimeSB - startTimeSB) + " ms");


        // StringBuilder kullanımı
        StringBuilder sb2 = new StringBuilder("Merhaba");
        System.out.println("StringBuffer: " + sb); // Output: Merhaba

        // StringBuilder'i manipüle etme
        long startTimeSB2 = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            sb.append(" yeni"); // Orijinal nesne güncellendi
        }
        long endTimeSB2 = System.currentTimeMillis();
        System.out.println("StringBuffer Manipülasyonu Süresi: " + (endTimeSB2 - startTimeSB2) + " ms");

        // Sonuçları karşılaştır
        System.out.println("\nSonuçlar:");
        System.out.println("String Manipülasyonu Süresi: " + (endTimeString - startTimeString) + " ms");
        System.out.println("StringBuffer Manipülasyonu Süresi: " + (endTimeSB - startTimeSB) + " ms");
        System.out.println("StringBuilder Manipülasyonu Süresi: " + (endTimeSB2 - startTimeSB2) + " ms");
    }
}
