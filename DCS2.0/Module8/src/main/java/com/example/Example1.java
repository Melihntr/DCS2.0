package com.example;

import com.example2.Module33.BirdA;
import com.example2.Module33.ExampleClassA;
public class Example1 {
    public static void main(String[] args){
        com.example2.Module33.ExampleClassA c = new com.example2.Module33.ExampleClassA();
        c.sayHello();
        com.example2.Module33.BirdA bird = new com.example2.Module33.BirdA("Kuş1");
        System.out.println(bird.getName());
        bird.makeSound();
        bird.fly();
        c.sayGoodbye();

    }
}