package com.melih.spring.E4;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {
    @Value("${app.title}")
    private String title;

    @Value("${app.env}")
    private String env;

    public void printConfig() {
        System.out.println("Title: " + title);
        System.out.println("Environment: " + env);
    }
}
