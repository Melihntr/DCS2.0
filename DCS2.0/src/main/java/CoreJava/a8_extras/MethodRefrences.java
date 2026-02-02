package CoreJava.a8_extras;
import java.util.*;
import java.util.function.Function;

public class MethodRefrences {
    public static void main(String[] args) {
        List numbers = Arrays.asList(1, 2, 3, 4, 5);
        numbers.forEach(System.out::println); //Statik Method Referansı

        List<String> name1 = Arrays.asList("A", "B");
        name1.forEach(String::toUpperCase); //Nesne Method Referansı

    }
}

