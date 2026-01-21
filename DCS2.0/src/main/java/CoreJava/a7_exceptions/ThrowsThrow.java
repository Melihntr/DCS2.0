package CoreJava.a7_exceptions;

// Throws ve Throw
class Main4 {
    public static void main(String[] args) {
        try {
            method();
        } catch (Exception e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }

    public static void method() throws Exception {
        throw new Exception("Manuel olarak atılan hata");
    }
}