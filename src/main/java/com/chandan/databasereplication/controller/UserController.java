package com.chandan.databasereplication.controller;

import com.chandan.databasereplication.dto.userDto;
import com.chandan.databasereplication.model.User;
import com.chandan.databasereplication.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody Map<String, String> body) {
        return service.save(
                body.get("name"),
                body.get("email")
        );
    }

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable Long id) {
        return service.findById(id);
    }
}
