package CleanCode;

import java.util.ArrayList;
import java.util.List;

/**
 * C1.1: İsimlendirme (Naming)
 * Amaç: Kodun yorum satırına gerek kalmadan kendini anlatması.
 */
public class NamingConvention {

    // KÖTÜ YAKLAŞIM
    public void process() {
        List<int[]> l1 = new ArrayList<>(); // l1 nedir? int[] ne tutuyor?
        int d = 14; // d nedir?
        
        for (int i = 0; i < d; i++) {
            // ...
        }
    }

    // İYİ YAKLAŞIM
    public void processStudentGrades() {
        // İsimlendirme niyet belirtir
        final int MAX_EXAM_DAYS = 14; 
        List<int[]> studentScoresPerSemester = new ArrayList<>();
        
        int elapsedDaysSinceExam = 10;
        
        if (elapsedDaysSinceExam < MAX_EXAM_DAYS) {
            System.out.println("Sınav sonuçları henüz açıklanmadı.");
        }
    }

    public static void main(String[] args) {
        NamingConvention example = new NamingConvention();
        example.processStudentGrades();
    }
}