package AdvancedJava.b4_JVM_internals;

public class ReferenceTypes {
    public static void main(String[] args) {
        // Referans türleri: String, Integer, Double gibi
        String str = "Java"; // str, takma bellekteki String nesnesine referans verir
        Integer sayi = new Integer(10); // sayi, takma bellekteki Integer nesnesine referans verir
        // Primitive türler: int, double, boolean gibi
        int primitiveSayi = 20; // primitiveSayi, yığın bellekte saklanır

        // Referansların nasıl çalıştığını gösteren bir örnek
        changeString(str);
        System.out.println("str: " + str); // str hala "Java" olur çünkü String immutabledır

        changeInteger(sayi);
        System.out.println("sayi: " + sayi); // sayi'nin değeri değişir
    }

    public static void changeString(String str) {
        str = "Yeni Değer"; // Bu değişiklik, orijinal str'yi etkilemez
    }

    public static void changeInteger(Integer sayi) {
        sayi = new Integer(30); // Bu değişiklik, orijinal sayi'yi etkiler çünkü referanslar kullanılır
    }
}
