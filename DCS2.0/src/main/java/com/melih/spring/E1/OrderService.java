package com.melih.spring.E1;


import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final PaymentService paymentService;

    // Constructor injection (recommended)
    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void placeOrder(double amount) {
        paymentService.charge(amount);
    }
}
