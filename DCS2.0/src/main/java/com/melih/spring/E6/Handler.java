package com.melih.spring.E6;

public class Handler {
    public void process(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty");
        }
        System.out.println("Processed: " + input);
    }
}
