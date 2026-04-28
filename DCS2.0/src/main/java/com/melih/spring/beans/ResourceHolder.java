package com.melih.spring.beans;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class ResourceHolder {

    public ResourceHolder() {
        System.out.println("ResourceHolder: constructor");
    }

    @PostConstruct
    public void init() {
        System.out.println("ResourceHolder: @PostConstruct");
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("ResourceHolder: @PreDestroy");
    }
}
