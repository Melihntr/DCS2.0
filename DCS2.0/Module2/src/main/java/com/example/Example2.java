package com.example;

import com.example.ModuleA.BirdA;
import com.example.ModuleA.ExampleClassA;
public class Example2 {
    public static void main(String[] args){
        ExampleClassA c = new ExampleClassA();
        c.sayHello();
        BirdA bird = new BirdA("Kuş2");
        System.out.println(bird.getName());
        bird.makeSound();
        bird.fly();
        c.sayGoodbye();

    }
}
