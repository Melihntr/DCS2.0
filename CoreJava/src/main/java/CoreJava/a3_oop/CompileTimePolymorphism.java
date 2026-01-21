package CoreJava.a3_oop;

class Matematik {
    public int topla(int a, int b) {
        return a + b;
    }

    public double topla(double a, double b) {
        return a + b;
    }

    public int topla(int a, int b, int c) {
        return a + b + c;
    }
}
class Main2 {
    public static void main(String[] args) {
        Matematik matematik = new Matematik();
        System.out.println(matematik.topla(1, 2)); // 3
        System.out.println(matematik.topla(1.5, 2.5)); // 4.0
        System.out.println(matematik.topla(1, 2, 3)); // 6
    }
}
