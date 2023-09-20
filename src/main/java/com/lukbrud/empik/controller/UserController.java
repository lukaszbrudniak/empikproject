package com.lukbrud.empik.controller;

import com.lukbrud.empik.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public interface UserController {

    @GetMapping("/{login}")
    ResponseEntity<User> getUser(@PathVariable String login);
}
