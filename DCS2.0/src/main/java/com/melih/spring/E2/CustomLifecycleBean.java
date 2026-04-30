package com.melih.spring.E2;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class CustomLifecycleBean implements InitializingBean, DisposableBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Custom init (InitializingBean)");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("Custom destroy (DisposableBean)");
    }
}