package com.melih.spring.E2;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class PrototypeService {
    public PrototypeService() {
        System.out.println("Prototype bean oluşturuldu.");
    }

    public void cleanup() {
        System.out.println("Manuel temizlik.");
    }
}
