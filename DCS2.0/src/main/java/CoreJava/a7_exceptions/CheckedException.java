package CoreJava.a7_exceptions;

// Checked Exception Örneği
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class Main {
    public static void main(String[] args) {
        try {
            File file = new File("example.txt");
            FileReader reader = new FileReader(file);
        } catch (IOException e) {
            System.out.println("Dosya okunurken hata oluştu: " + e.getMessage());
        }
    }
}
