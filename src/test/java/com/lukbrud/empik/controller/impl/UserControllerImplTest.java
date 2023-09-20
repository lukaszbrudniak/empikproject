package com.lukbrud.empik.controller.impl;

import com.lukbrud.empik.model.User;
import com.lukbrud.empik.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserControllerImplTest {

    private UserControllerImpl userController;

    @Mock
    private UserService userService;

    private PodamFactory podamFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userController = new UserControllerImpl(userService);
        podamFactory = new PodamFactoryImpl();
    }

    @Test
    void shouldReturnUserWhenFound() {
        // Given
        String login = "testUser";
        User user = podamFactory.manufacturePojo(User.class);
        when(userService.getUser(login)).thenReturn(Optional.of(user));

        // When
        ResponseEntity<User> response = userController.getUser(login);

        // Then
        verify(userService).getUser(login);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void shouldReturnNotFoundWhenUserNotFound() {
        // Given
        String login = "nonExistentUser";
        when(userService.getUser(login)).thenReturn(Optional.empty());

        // When
        ResponseEntity<User> response = userController.getUser(login);

        // Then
        verify(userService).getUser(login);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}