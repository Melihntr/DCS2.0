package com.melih.spring.E2;

public class PrototypeBean {
    public PrototypeBean() {
        System.out.println("PrototypeBean constructor called.");
    }

    public void init() {
        System.out.println("PrototypeBean init method called.");
    }

    public void destroy() {
        System.out.println("PrototypeBean destroy method called.");
    }

    public void doSomething() {
        System.out.println("PrototypeBean is working.");
    }
}
