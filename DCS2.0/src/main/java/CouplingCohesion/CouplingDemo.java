package main.java.CouplingCohesion;

/**
 * 3.3 & 3.4: Coupling (Bağımlılık) Çeşitleri
 * Amaç: Nesneleri birbirinden izole ederek değişikliğin etkisini minimize etmek.
 */
public class CouplingDemo {

    // --- KÖTÜ YAKLAŞIM (Content Coupling) ---
    // Bir sınıf diğerinin alanlarına doğrudan erişiyor.
    static class EngineBad {
        public int status = 0; // Public alan: Kapsülleme (Encapsulation) ihlali.
    }
    
    static class CarBad {
        void start(EngineBad e) { 
            e.status = 1; // Sınıfın iç durumunu dışarıdan zorla değiştiriyor.
        }
    }

    // --- İYİ YAKLAŞIM (Message Coupling) ---
    // En sağlıklı bağımlılık; sadece metod çağrısı (mesaj) kullanılır.
    interface IEngine { 
        void start(); 
    }

    static class EngineGood implements IEngine {
        private int status = 0; // Durum gizli (private).
        
        @Override
        public void start() { 
            this.status = 1; 
            System.out.println("Mesaj Alındı: Motor kendi iç mekanizmasıyla çalıştı."); 
        }
    }

    static class CarGood {
        // CarGood, motorun içindeki 'status' değişkenini bilmez, sadece 'start()' der.
        void drive(IEngine engine) { 
            engine.start(); 
        }
    }

    public static void main(String[] args) {
        // Doğru Uygulama (Good Practice)
        IEngine myEngine = new EngineGood();
        CarGood myCar = new CarGood();
        
        System.out.println("--- Message Coupling (İyi) Çalışıyor ---");
        myCar.drive(myEngine); 
    }
}
