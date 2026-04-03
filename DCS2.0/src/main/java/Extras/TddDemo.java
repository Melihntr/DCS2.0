package Extras;

/**
 * 5.1 & 5.2: TDD Döngüsü Uygulaması
 * Senaryo: Bir hesap makinesi için bölme işlemi geliştirme.
 */
public class TddDemo {

    // 🔴 ADIM 1: RED (Başarısız Test)
    // Henüz divide metodu yokken test yazılır. Kod derlenmez (Red).
    /*
    @Test
    void shouldDivideNumbers() {
        Calculator calc = new Calculator();
        assertEquals(5, calc.divide(10, 2));
    }
    */

    // 🟢 ADIM 2: GREEN (Geçen Test)
    // Testi geçirecek en basit ve hızlı kod yazılır.
    class Calculator {
        public int divide(int a, int b) {
            return a / b; // Test artık geçer!
        }
    }

    // 🔵 ADIM 3: REFACTOR (Düzenleme)
    // Test hala geçerken kod Clean Code prensiplerine göre iyileştirilir.
    class CalculatorRefactored {
        public int divide(int a, int b) {
            if (b == 0) {
                throw new ArithmeticException("Sıfıra bölünemez!");
            }
            return a / b;
        }
    }

    public static void main(String[] args) {
        System.out.println("TDD: Güvenli ve test edilebilir kod tasarımı tamamlandı.");
    }
}
