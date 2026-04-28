package com.melih.spring.beans;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class TransientResource {

    public TransientResource() { System.out.println("TransientResource: constructor"); }

    @PostConstruct
    public void init() { System.out.println("TransientResource: @PostConstruct"); }

    @PreDestroy
    public void cleanup() { System.out.println("TransientResource: @PreDestroy (won't be called by container for prototype)"); }
}
