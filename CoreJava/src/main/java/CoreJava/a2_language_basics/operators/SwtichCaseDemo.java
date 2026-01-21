package CoreJava.a2_language_basics.operators;

public class SwtichCaseDemo {
    public static void main(String[] args) {
        int gun = 3;

        switch (gun) {
            case 1:
                System.out.println("Pazartesi"); // Output: Pazartesi
                break;
            case 2:
                System.out.println("Salı");
                break;
            case 3:
                System.out.println("Çarşamba");
                break;
            default:
                System.out.println("Geçersiz gün");
        }
    }
}
