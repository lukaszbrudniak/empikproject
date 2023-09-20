package com.lukbrud.empik.service;

import com.lukbrud.empik.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    Optional<User> getUser(String login);
}

