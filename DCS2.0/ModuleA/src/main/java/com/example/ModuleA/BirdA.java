package com.example.ModuleA;


// Abstract sınıf
abstract class AbstractAnimalA {
    private String name;

    public AbstractAnimalA(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void makeSound();
}

// Interface
interface FlyableA {
    void fly();
}

// Hem abstract sınıfı genişleten hem de interface'i uygulayan sınıf
public class BirdA extends AbstractAnimalA implements FlyableA {
    public BirdA(String name) {
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
