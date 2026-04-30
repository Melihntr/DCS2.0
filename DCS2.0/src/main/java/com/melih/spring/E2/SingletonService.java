package com.melih.spring.E2;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class SingletonService {
    public void doSomething() {
        System.out.println("Singleton service çalışıyor.");
    }
}