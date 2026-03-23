package CouplingCohesion;

/**
 * 3.1 & 3.2: Cohesion (Birliktelik) Çeşitleri
 * Amaç: Sınıf içindeki sorumlulukları odaklanmış (Functional) hale getirmek.
 */
public class CohesionDemo {

    // ❌ KÖTÜ: Coincidental Cohesion (Tesadüfi Birliktelik)
    // Birbiriyle alakasız işler aynı sınıfta (Utils/Helpers gibi).
    class RandomUtils {
        public void saveToDb() { /* DB işi */ }
        public void printInvoice() { /* UI işi */ }
        public void calculateTax() { /* Mantıksal iş */ }
    }

    // ✅ İYİ: Functional Cohesion (Fonksiyonel Birliktelik)
    // En iyi durumdur; sınıf tek ve spesifik bir amaca odaklanır.
    class TaxCalculator {
        public double calculateVat(double price) { return price * 0.20; }
        public double calculateIncomeTax(double total) { return total * 0.15; }
    }

    public static void main(String[] args) {
        System.out.println("Cohesion: Odaklanmış sınıfların bakımı daha kolaydır.");
    }
}