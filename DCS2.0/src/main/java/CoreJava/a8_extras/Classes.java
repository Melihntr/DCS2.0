package CoreJava.a8_extras;

// Static Nested Class
class Outer {
    static class Nested {
        public static void main(String[] args) {
            System.out.println("Static Nested Class");
        }
    }
}

// Inner Class
class OuterInner {
    class Inner {
        public void print() {
            System.out.println("Inner Class");
        }
    }

    public static void main(String[] args) {
        OuterInner outer = new OuterInner();
        OuterInner.Inner inner = outer.new Inner();
        inner.print();
    }
}

// Local Class
class LocalClassExample {
    void method() {
        class Local {
            public void print() {
                System.out.println("Local Class");
            }
        }
        Local local = new Local();
        local.print();
    }

    public static void main(String[] args) {
        LocalClassExample example = new LocalClassExample();
        example.method();
    }
}

// Anonymous Class
interface Runnable {
    void run();
}
