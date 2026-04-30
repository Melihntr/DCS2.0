package com.melih.spring.E7;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = context.getBean(UserService.class);

        // UserService metodları çağrılır
        System.out.println(userService.getUserById(1));
        userService.saveUser("JohnDoe");
    }
}
