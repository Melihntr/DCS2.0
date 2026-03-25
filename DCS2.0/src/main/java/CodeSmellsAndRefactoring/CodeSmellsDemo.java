package CodeSmellsAndRefactoring;

/**
 * 4.2: Code Smells Örnekleri
 * A. Bloaters (Şişkinler): Dev metodlar veya sınıflar.
 * B. OO Abusers: Polimorfizm yerine switch/if kullanımı.
 */
public class CodeSmellsDemo {

    // KOKU: Bloaters - Long Method & Data Clumps
    // startDate ve endDate sürekli beraber geziyor (Data Clump).
    public void printReport(String title, String startDate, String endDate) {
        // 50 satırlık rapor hazırlama mantığı... (Long Method)
    }

    // KOKU: Object-Orientation Abuser - Switch Statements
    // Yeni tip eklendiğinde bu metodun sürekli değişmesi gerekir (OCP ihlali).
    public double getSpeed(String birdType) {
        switch (birdType) {
            case "EUROPEAN": return 10.0;
            case "AFRICAN": return 12.0;
            default: return 0;
        }
    }

    public static void main(String[] args) {
        System.out.println("Code Smells: Tasarım hataları tespit edildi.");
    }
}