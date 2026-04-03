package SOLID;

/**
 * 2.5: DIP - Bağımlılığın Ters Çevrilmesi
 * Amaç: Yüksek seviyeli modüllerin düşük seviyeli modüllere değil, soyutlamalara bağımlı olması.
 */
public class DependencyInversionDemo {

    // --- KÖTÜ YAKLAŞIM (Sıkı Bağımlılık) ---
    
    // Somut bir sınıf (Düşük Seviyeli Modül)
    static class EmailService {
        public void sendEmail(String msg) {
            System.out.println("Email gönderiliyor: " + msg);
        }
    }

    // İHLAL: NotificationBad doğrudan EmailService'e bağımlı.
    // Yarın SMS eklemek isterseniz bu sınıfı komple değiştirmeniz gerekir.
    static class NotificationBad {
        private EmailService service = new EmailService(); // Sıkı bağımlılık
        
        public void notify(String msg) {
            service.sendEmail(msg);
        }
    }

    // --- İYİ YAKLAŞIM (DIP Uygun) ---

    // SOYUTLAMA: Her iki modül de bu arayüze bağımlı olur.
    interface MessageService { 
        void send(String msg); 
    }

    // Somut gerçekleştirim 1
    static class SmsService implements MessageService {
        @Override
        public void send(String msg) { 
            System.out.println("SMS Gönderildi: " + msg); 
        }
    }

    // Somut gerçekleştirim 2
    static class EmailServiceClean implements MessageService {
        @Override
        public void send(String msg) { 
            System.out.println("Email Gönderildi: " + msg); 
        }
    }

    // UYGUN: NotificationGood sadece MessageService arayüzünü tanır.
    static class NotificationGood {
        private final MessageService service;

        // Dependency Injection (Bağımlılık Enjeksiyonu): Bağımlılık dışarıdan gelir.
        public NotificationGood(MessageService service) { 
            this.service = service; 
        }
        
        public void notifyUser(String msg) { 
            service.send(msg); 
        }
    }

    public static void main(String[] args) {
        // Çalışma anında istenilen servis enjekte edilebilir.
        MessageService sms = new SmsService();
        NotificationGood notification = new NotificationGood(sms);
        notification.notifyUser("Merhaba DIP (SMS üzerinden)!");

        // Mevcut yapıyı bozmadan Email'e geçiş yapılabilir.
        MessageService email = new EmailServiceClean();
        NotificationGood notificationEmail = new NotificationGood(email);
        notificationEmail.notifyUser("Merhaba DIP (Email üzerinden)!");
    }
}
