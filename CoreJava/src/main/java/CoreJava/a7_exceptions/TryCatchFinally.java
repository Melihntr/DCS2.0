package CoreJava.a7_exceptions;

// Try-Catch Blokları
class Main3 {
    public static void main(String[] args) {
        try {
            // Kod bloğu
            int[] array = new int[5];
            System.out.println(array[10]);
        } catch (ArrayIndexOutOfBoundsException e) {
            // Exception処理
            System.out.println("Dizi indeks hatası: " + e.getMessage());
        } finally {
            // Her zaman çalışan kod bloğu
            System.out.println("Finally bloğu çalıştı");
        }
    }
}