// Implementasyon arayüzü
interface Color {
    String fill();
}

class RedColor implements Color {
    @Override
    public String fill() { return "Kırmızı"; }
}

class BlueColor implements Color {
    @Override
    public String fill() { return "Mavi"; }
}

// Soyutlama
abstract class Shape {
    protected Color color;

    public Shape(Color color) {
        this.color = color;
    }

    abstract void draw();
}

// Konkret soyutlama
class Circle extends Shape {
    public Circle(Color color) {
        super(color);
    }

    @Override
    void draw() {
        System.out.println("Çember " + color.fill() + " renkte çizildi.");
    }
}

// Kullanım
public class BridgePatternDemo {
    public static void main(String[] args) {
        Shape redCircle = new Circle(new RedColor());
        Shape blueCircle = new Circle(new BlueColor());

        redCircle.draw();
        blueCircle.draw();
    }
}
