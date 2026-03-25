package main.java.SOLID;

/**
 * 2.1: SRP - Tek Sorumluluk Prensibi
 */
public class SingleResponsibilityDemo {

    // İHLAL: Hem veri tutuyor, hem hesaplıyor, hem kaydediyor (God Object).
    class EmployeeBad {
        public void calculatePay() { /* Muhasebe mantığı */ }
        public void saveToDatabase() { /* Veritabanı mantığı */ }
    }

    // UYGUN: Sorumluluklar ayrıştırılmış.
    class EmployeeData { /* Sadece çalışan verisi */ }
    
    class PayCalculator { 
        public void calculate(EmployeeData emp) { System.out.println("Maaş hesaplandı."); } 
    }
    
    class EmployeeRepository { 
        public void save(EmployeeData emp) { System.out.println("Veritabanına kaydedildi."); } 
    }

    public static void main(String[] args) {
        System.out.println("SRP: Sorumluluklar başarıyla ayrıldı.");
    }
}