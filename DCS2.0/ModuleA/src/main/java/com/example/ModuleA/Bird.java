package com.example.ModuleA;

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
public class Bird extends AbstractAnimal implements Flyable {
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



