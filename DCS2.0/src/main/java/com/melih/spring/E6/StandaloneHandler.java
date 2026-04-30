package com.melih.spring.E6;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Date;

public class StandaloneHandler {
    public static void main(String[] args) {
        Handler handler = new Handler();
        try {
            handler.process("Test Input");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
