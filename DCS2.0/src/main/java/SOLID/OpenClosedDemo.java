package SOLID;

/**
 * 2.2: OCP - Açık/Kapalı Prensibi
 * Amaç: Yazılım birimlerinin genişletilmeye açık, ancak değiştirilmeye kapalı olması.
 */
public class OpenClosedDemo {

    // --- KÖTÜ YAKLAŞIM (Değişime Dirençli) ---

    // İHLAL: Yeni bir ödeme tipi (örn. ApplePay) eklemek için bu metodu modifiye etmeniz gerekir.
    static class PaymentProcessorBad {
        public void process(String type) {
            if (type.equals("CreditCard")) { 
                System.out.println("Kredi kartı ile ödendi."); 
            } else if (type.equals("PayPal")) { 
                System.out.println("PayPal ile ödendi."); 
            }
            // Her yeni tipte buraya yeni bir 'else if' eklemek OCP ihlalidir.
        }
    }

    // --- İYİ YAKLAŞIM (OCP Uygun) ---

    // GENİŞLETİLEBİLİR YAPI: Arayüz (Interface) kullanımı.
    interface PaymentMethod { 
        void process(); 
    }

    static class CreditCardPayment implements PaymentMethod {
        @Override
        public void process() { 
            System.out.println("Kredi kartı ile ödendi (OCP Uygun)."); 
        }
    }

    // ✅ YENİ ÖZELLİK: Mevcut PaymentProcessor koduna dokunmadan yeni bir sınıf eklendi.
    static class CryptoPayment implements PaymentMethod {
        @Override
        public void process() { 
            System.out.println("Kripto ile ödendi (OCP Uygun)."); 
        }
    }

    // Bu sınıf artık her türlü PaymentMethod'u destekler ve asla değişmez.
    static class PaymentProcessorClean {
        public void checkout(PaymentMethod method) {
            method.process();
        }
    }

    public static void main(String[] args) {
        // Sınıflar static olduğu için main içerisinden doğrudan erişilebilir.
        PaymentProcessorClean processor = new PaymentProcessorClean();

        // Kripto ödeme yöntemi kullanılıyor
        PaymentMethod crypto = new CryptoPayment();
        processor.checkout(crypto);

        // Kredi kartı ödeme yöntemi kullanılıyor
        PaymentMethod card = new CreditCardPayment();
        processor.checkout(card);
    }
}