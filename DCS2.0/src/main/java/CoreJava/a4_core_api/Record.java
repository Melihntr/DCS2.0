package CoreJava.a4_core_api;

record Kisi(String ad, int yas) {
    public void yazdir() {
        System.out.println("Ad: " + ad);
        System.out.println("Yaş: " + yas);
    }

    public static void main(String[] args) {
        Kisi kisi = new Kisi("John", 30);
        kisi.yazdir();
    }
}
