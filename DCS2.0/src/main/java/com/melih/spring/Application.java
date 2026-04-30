package com.melih.spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.melih.spring.E1.OrderService;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner runner(OrderService orderService) {
		return args -> orderService.placeOrder(99.99); // orderService bean olarak inject ediliyor
	}
}



