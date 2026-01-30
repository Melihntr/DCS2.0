package com.example.ModuleB;

import CoreJava.a3_oop.Bird;
public class ExampleB {
    public static void main(String[] args){
        Bird bird = new Bird("Kuş");
        System.out.println(bird.getName());
        bird.makeSound();
        bird.fly();

    }
}
