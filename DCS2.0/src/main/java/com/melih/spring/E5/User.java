package com.melih.spring.E5;

import jakarta.validation.constraints.*;

public class User {
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotNull(message = "Age cannot be null")
    @Min(value = 18, message = "Age must be at least 18")
    private Integer age;

    @Email(message = "Email should be valid")
    private String email;

    // Getter ve setter metodları
}
