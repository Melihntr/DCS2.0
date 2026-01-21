package CoreJava.a4_core_api;

class Araba {
    private String marka;
    private String model;

    public Araba(String marka, String model) {
        this.marka = marka;
        this.model = model;
    }

    public void yazdir() {
        System.out.println("Marka: " + marka);
        System.out.println("Model: " + model);
    }

    public static void main(String[] args) {
        // Reference
        Araba araba1 = new Araba("Toyota", "Corolla");
        Araba araba2 = araba1; // araba2, araba1'e referans gösterir

        // Object (Instance)
        System.out.println("Araba 1:");
        araba1.yazdir();

        // Class
        System.out.println("Araba sınıfının adı: " + araba1.getClass().getName());
    }
}
