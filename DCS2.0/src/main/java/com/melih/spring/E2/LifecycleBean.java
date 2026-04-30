package com.melih.spring.E2;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class LifecycleBean {

    public LifecycleBean() {
        System.out.println("1. Constructor: Bean oluşturuldu.");
    }

    @PostConstruct
    public void init() {
        System.out.println("2. @PostConstruct: Başlatma işlemleri.");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("3. @PreDestroy: Temizlik işlemleri.");
    }
}
