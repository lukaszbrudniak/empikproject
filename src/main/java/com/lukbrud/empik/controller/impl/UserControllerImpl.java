package com.lukbrud.empik.controller.impl;

import com.lukbrud.empik.controller.UserController;
import com.lukbrud.empik.model.User;
import com.lukbrud.empik.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Implementation of the UserController interface.
 * Provides endpoints for retrieving user data.
 *
 * @author Lukasz Brudniak
 */
@AllArgsConstructor
@RestController
class UserControllerImpl implements UserController {

    private UserService userService;

    @Override
    public ResponseEntity<User> getUser(String login) {
        Optional<User> user = userService.getUser(login);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}