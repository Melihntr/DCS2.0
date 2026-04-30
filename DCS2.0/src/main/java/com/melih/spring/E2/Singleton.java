package com.melih.spring.E2;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

@Component
public class Singleton {
    @Autowired
    private ObjectProvider<PrototypeBean> prototypeProvider;

    public void usePrototype() {
        PrototypeBean p = prototypeProvider.getObject(); // Her çağrısı yeni bir instance döner
        p.doSomething();
    }
}

@Component
class Singleton2 {
    @Lookup
    public PrototypeBean getPrototype() {
        return null; // Spring bu metodu override eder
    }

    public void usePrototype() {
        PrototypeBean p = getPrototype();
        p.doSomething();
    }
}
