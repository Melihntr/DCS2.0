package CoreJava.a2_language_basics.operators;

public class Loops {
    public static void main(String[] args) {
        for (int i = 1; i <= 5; i++) {
            System.out.println("i: " + i); // Output: 1, 2, 3, 4, 5
        }
        System.out.println();
        System.out.println("End of for loop");
        System.out.println();
        int i = 1;
        while (i <= 5) {
            System.out.println("i: " + i); // Output: 1, 2, 3, 4, 5
            i++;
        }
        System.out.println();
        System.out.println("End of while loop");
        System.out.println();

         i = 1;
        do {
            System.out.println("i: " + i); // Output: 1, 2, 3, 4, 5
            i++;
        } while (i <= 5);
        System.out.println();
        System.out.println("End of do-while loop");
    }
}
