package com.melih.spring.E7;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {

    @Before("execution(* com.example.service.UserService.saveUser(..))")
    public void checkPermission() {
        // Basit bir güvenlik kontrolü
        if (!"admin".equals(System.getProperty("user"))) {
            throw new SecurityException("Yetkisiz erişim!");
        }
    }
}
