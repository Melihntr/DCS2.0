package CoreJava.a3_oop;

class Box {
    int length;

    Box() { // Default constructor
        this(5); // this(5) diğer constructor'ı çağırır.
    }

    Box(int l) { // Parametreli constructor
        this.length = l;
    }
}

