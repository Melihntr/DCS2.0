package main.java.SOLID;

/**
 * 2.4: ISP - Arayüz Ayrıştırma Prensibi
 * Amaç: İstemcileri kullanmadıkları metodlara zorlamamak için büyük arayüzleri parçalamak.
 */
public class InterfaceSegregationDemo {

    // --- KÖTÜ YAKLAŞIM (Şişkin Arayüz) ---

    // İHLAL: Bu arayüz çok fazla sorumluluk içeriyor.
    interface WorkerBad { 
        void work(); 
        void eat(); 
    }

    static class RobotWorkerBad implements WorkerBad {
        @Override
        public void work() { System.out.println("Robot çalışıyor."); }

        @Override
        public void eat() {
            // Robot yemek yiyemez ama bu metodu implement etmeye zorlandı.
            throw new UnsupportedOperationException("Robotlar yemek yiyemez!");
        }
    }

    // --- İYİ YAKLAŞIM (ISP Uygun) ---

    // ÇÖZÜM: Spesifik ve küçük arayüzler.
    interface Workable { void work(); }
    interface Feedable { void eat(); }

    // Robot sadece ilgili olduğu arayüzü alır.
    static class RobotWorker implements Workable {
        @Override
        public void work() { System.out.println("Robot çalışıyor (ISP Uygun)."); }
    }

    // İnsan her iki yeteneğe de sahip olduğu için ikisini de uygular.
    static class HumanWorker implements Workable, Feedable {
        @Override
        public void work() { System.out.println("İnsan çalışıyor."); }
        
        @Override
        public void eat() { System.out.println("İnsan yemek yiyor."); }
    }

    public static void main(String[] args) {
        // Sınıflar static olduğu için doğrudan nesne oluşturulabilir.
        Workable robot = new RobotWorker();
        robot.work();

        HumanWorker human = new HumanWorker();
        human.work();
        human.eat();
    }
}