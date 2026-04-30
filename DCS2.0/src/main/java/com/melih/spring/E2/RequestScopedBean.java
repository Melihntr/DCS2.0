package com.melih.spring.E2;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
public class RequestScopedBean {
    public RequestScopedBean() {
        System.out.println("Request scope bean oluşturuldu.");
    }
}

@Component
@Scope("session")
class SessionScopedBean {
    public SessionScopedBean() {
        System.out.println("Session scope bean oluşturuldu.");
    }
}
