package CoreJava.a2_language_basics;

public class StaticDemo {
    private static int sayac = 0; // Static değişken

    public StaticDemo() {
        sayac++;
    }

    public static void main(String[] args) {
        System.out.println("StaticOrnek.main()");
        StaticDemo ornek1 = new StaticDemo();
        StaticDemo ornek2 = new StaticDemo();
        System.out.println("Sayac: " + sayac);
    }
}


