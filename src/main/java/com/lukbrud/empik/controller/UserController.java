package com.lukbrud.empik.controller;

import com.lukbrud.empik.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller interface for user-related endpoints.
 * Defines an endpoint for retrieving user data.
 *
 * @author Lukasz Brudniak
 */
@RestController
@RequestMapping("/users")
public interface UserController {

    /**
     * Retrieves user data by login.
     *
     * @param login The login of the user to retrieve.
     * @return ResponseEntity containing user data or a not found response.
     */
    @GetMapping("/{login}")
    ResponseEntity<User> getUser(@PathVariable String login);
}
