package CoreJava.a8_extras;

class Main {
    public static void main(String[] args) {
        // Autoboxing
        int primitiveInt = 10;
        Integer wrapperInt = primitiveInt; // Autoboxing
        System.out.println("Autoboxing: " + wrapperInt);

        // Unboxing
        Integer wrapperInteger = 20;
        int primitiveInteger = wrapperInteger; // Unboxing
        System.out.println("Unboxing: " + primitiveInteger);

        // String Concatenation
        String str1 = "Hello";
        String str2 = " World";
        String result = str1 + str2; // String Concatenation
        System.out.println("String Concatenation: " + result);

        // Autoboxing ve Unboxing ile String Concatenation
        int num1 = 10;
        int num2 = 20;
        String concatResult = "Result: " + num1 + num2; // Autoboxing ve String Concatenation
        System.out.println(concatResult);

        // Doğru kullanım
        int num3 = 10;
        int num4 = 20;
        String correctConcatResult = "Result: " + (num3 + num4); // Doğru kullanım
        System.out.println(correctConcatResult);
    }
}
