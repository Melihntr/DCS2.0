package com.example;

import com.example.ModuleA.Bird;
import com.example.ModuleA.ExampleClassA;
public class Example1 {
    public static void main(String[] args){
        ExampleClassA c = new ExampleClassA();
        c.sayHello();
        Bird bird = new Bird("Kuş");
        System.out.println(bird.getName());
        bird.makeSound();
        bird.fly();
        c.sayGoodbye();

    }
}