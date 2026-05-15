package com.melih.data.Controller;

import com.melih.data.DTO.UserDto;
import com.melih.data.Entity.User;
import com.melih.data.Service.UserService;
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
            @RequestBody String username
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
