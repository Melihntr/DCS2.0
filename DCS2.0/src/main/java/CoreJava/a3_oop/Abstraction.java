package CoreJava.a3_oop;

// Abstract sınıf
abstract class AbstractAnimal {
    private String name;

    public AbstractAnimal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void makeSound();
}

// Interface
interface Flyable {
    void fly();
}

// Hem abstract sınıfı genişleten hem de interface'i uygulayan sınıf
class Bird extends AbstractAnimal implements Flyable {
    public Bird(String name) {
        super(name);
    }

    @Override
    public void makeSound() {
        System.out.println("Bird is singing");
    }

    @Override
    public void fly() {
        System.out.println("Bird is flying");
    }
}

class Main3 {
    public static void main(String[] args) {
        Bird bird = new Bird("Kuş");
        System.out.println(bird.getName());
        bird.makeSound();
        bird.fly();
    }
}
