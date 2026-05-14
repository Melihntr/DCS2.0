package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public User create(
            @RequestParam String username
    ) {
        return userService.createUser(username);
    }

    @GetMapping
    public List<User> find(
            @RequestParam String username
    ) {
        return userService.findByUsername(username);
    }

    @GetMapping("/criteria")
    public List<User> criteria(
            @RequestParam String username
    ) {
        return userService.criteriaSearch(username);
    }

    @GetMapping("/dto")
    public List<UserDto> dto() {
        return userService.getDtos();
    }
}
