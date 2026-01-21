package CoreJava.a1_ecosystem;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class JarExample {
    public static void main(String[] args) {
        try {
            // Manifest dosyasını oluştur
            Manifest manifest = new Manifest();
            Attributes attributes = manifest.getMainAttributes();
            attributes.put(Attributes.Name.MANIFEST_VERSION, "1.0");
            attributes.put(Attributes.Name.MAIN_CLASS, "com.example.MainClass");

            // JarOutputStream oluştur
            JarOutputStream jarOutputStream = new JarOutputStream(
                    new java.io.FileOutputStream("example.jar"), manifest);

            // Jar dosyasını kapat
            jarOutputStream.close();

            System.out.println("JAR dosyası oluşturuldu: example.jar");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
