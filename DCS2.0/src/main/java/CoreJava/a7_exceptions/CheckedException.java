package CoreJava.a7_exceptions;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class Main {
    public static void main(String[] args) {
        File file = new File("example.txt");
        FileReader reader = null;

        try {
            reader = new FileReader(file);
            // Dosya okunurken işlemler...
            System.out.println("Dosya okunuyor...");
        } catch (IOException e) {
            System.out.println("Dosya okunurken hata oluştu: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Dosya okuyucu kapatılırken hata oluştu: " + e.getMessage());
                }
            }
        }

        try (FileReader reader2 = new FileReader(file)) {
            // Dosya okunurken işlemler...
            System.out.println("Dosya okunuyor...");
        } catch (IOException e) {
            System.out.println("Dosya okunurken hata oluştu: " + e.getMessage());
        }
    }
}
