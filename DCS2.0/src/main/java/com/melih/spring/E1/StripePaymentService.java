package com.melih.spring.E1;

import org.springframework.stereotype.Service;

public @Service // singleton by default
class StripePaymentService implements PaymentService {
    @Override
    public void charge(double amount) {
        System.out.println("Stripe charging: " + amount);
    }
}
