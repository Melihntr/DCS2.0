package com.melih.spring.E6;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
        // Kullanıcıyı veritabanından getir
        return ResponseEntity.ok("User found");
    }
}
