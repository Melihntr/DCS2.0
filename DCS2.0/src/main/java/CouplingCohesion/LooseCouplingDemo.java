package main.java.CouplingCohesion;

/**
 * 3.5: Abstract Coupling ve Loose Coupling
 * Amaç: Somut tipler yerine soyut tipler (Interface/Abstract) kullanarak esneklik sağlamak.
 */
public class LooseCouplingDemo {

    // Strateji arayüzü
    interface ILogger { 
        void log(String msg); 
    }

    // ÇÖZÜM: Sınıfları 'static' yaparak main metodundan erişilebilir hale getirdik.
    static class FileLogger implements ILogger {
        @Override
        public void log(String msg) { 
            System.out.println("Dosyaya loglandı: " + msg); 
        }
    }

    static class DbLogger implements ILogger {
        @Override
        public void log(String msg) { 
            System.out.println("Veritabanına loglandı: " + msg); 
        }
    }

    // Bağımlılık Kontrolü (Dependency Control)
    static class BusinessService {
        private final ILogger logger;

        // Gevşek bağlılık: Hangi logger'ın geldiğini bilmez, sadece ILogger olduğunu bilir.
        public BusinessService(ILogger logger) {
            this.logger = logger;
        }

        public void doWork() {
            // İş mantığı...
            logger.log("İşlem başarıyla tamamlandı.");
        }
    }

    public static void main(String[] args) {
        // Çalışma anında (runtime) bağımlılık seçilebilir.
        ILogger logger = new DbLogger(); 
        
        // Sınıflar static olduğu için artık 'demo.new' gibi karmaşık yapılara gerek kalmadı.
        BusinessService service = new BusinessService(logger);
        
        service.doWork();
    }
}
