package CoreJava.a3_oop;

class Animal {
    public void sound() {
        System.out.println("The animal makes a sound.");
    }
}
class Dog extends Animal {
    public void sound() {
        System.out.println("The dog barks.");
    }
}

