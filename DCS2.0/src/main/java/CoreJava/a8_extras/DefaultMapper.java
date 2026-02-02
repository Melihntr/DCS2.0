package CoreJava.a8_extras;

interface DefaultMapper {
    int topla(int a, int b);

    default void selamVer() {
        System.out.println("Merhaba!");
    }

    public class MatematikImpl implements DefaultMapper {
        @Override
        public int topla(int a, int b) {
            return a + b;
        }
    }

    public class Main {
        public static void main(String[] args) {
            DefaultMapper matematik = new MatematikImpl();
            matematik.selamVer(); // Merhaba! yazdırır

            int day = 5;
            switch (day) {
                case 1:
                System.out.println("Monday");
                break;
                case 2:
                System.out.println("Tuesday");
                break;
                // other cases
                default:
                System.out.println("Invalid day");
                break;
            }
        }
    }
}
