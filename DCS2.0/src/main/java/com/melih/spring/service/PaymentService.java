package com.melih.spring.service;
import org.springframework.stereotype.Service;

public interface PaymentService {
    void charge(double amount);
}