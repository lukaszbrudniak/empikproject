package com.lukbrud.empik.service.impl;

import com.lukbrud.empik.entity.UserEntity;
import com.lukbrud.empik.repository.UserRepository;
import com.lukbrud.empik.service.UserRepositoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserRepositoryServiceImplTest {

    private UserRepositoryService userRepositoryService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userRepositoryService = new UserRepositoryServiceImpl(userRepository);
    }

    @Test
    void shouldIncrementRequestCountWhenUserExists() {
        // Given
        String login = "testuser";

        UserEntity existingUserEntity = new UserEntity();
        existingUserEntity.setLogin(login);
        existingUserEntity.setRequestCount(5);

        when(userRepository.findById(login)).thenReturn(Optional.of(existingUserEntity));

        // When
        userRepositoryService.incrementRequestCount(login);

        // Then
        verify(userRepository, times(1)).save(existingUserEntity);
        assertEquals(6, existingUserEntity.getRequestCount());
    }

    @Test
    void shouldIncrementRequestCountWhenUserDoesNotExist() {
        // Given
        String login = "testuser";

        when(userRepository.findById(login)).thenReturn(Optional.empty());

        // When
        userRepositoryService.incrementRequestCount(login);

        // Then
        verify(userRepository, times(1)).save(argThat(userEntity ->
                userEntity.getLogin().equals(login) && userEntity.getRequestCount() == 1
        ));
    }
}