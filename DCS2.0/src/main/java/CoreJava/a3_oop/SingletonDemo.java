package CoreJava.a3_oop;

class Singleton {
    // Static instance
    private static Singleton instance;

    // Private constructor
    private Singleton() {}

    // Synchronized method to get instance
    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    // Example method
    public void showMessage() {
        System.out.println("Singleton instance");
    }

    public static void main(String[] args) {
        Singleton singleton1 = Singleton.getInstance();
        Singleton singleton2 = Singleton.getInstance();

        // Her iki nesne de aynı instance'a referans verir
        System.out.println(singleton1 == singleton2); // Output: true

        singleton1.showMessage(); // Output: Singleton instance
    }
}
