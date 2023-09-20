package com.lukbrud.empik.service.impl;

import com.lukbrud.empik.model.GithubUser;
import com.lukbrud.empik.model.User;
import com.lukbrud.empik.service.GithubApiService;
import com.lukbrud.empik.service.UserProcessingService;
import com.lukbrud.empik.service.UserRepositoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestClientException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserServiceImpl userService;

    @Mock
    private GithubApiService githubApiService;

    @Mock
    private UserProcessingService userProcessingService;

    @Mock
    private UserRepositoryService userRepositoryService;

    private PodamFactory podamFactory = new PodamFactoryImpl();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(githubApiService, userProcessingService, userRepositoryService);
    }

    @Test
    void testGetUserWithValidLogin() {
        String login = "testuser";
        GithubUser githubUser = new GithubUser();
        User user = podamFactory.manufacturePojo(User.class);


        when(githubApiService.fetchUserData(login)).thenReturn(githubUser);
        when(userProcessingService.processUserData(login, githubUser)).thenReturn(user);

        Optional<User> result = userService.getUser(login);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepositoryService, times(1)).incrementRequestCount(login);
    }

    @Test
    void testGetUserWithEmptyLogin() {
        String login = "";
        assertThrows(IllegalArgumentException.class, () -> userService.getUser(login));
        verifyNoInteractions(githubApiService);
        verifyNoInteractions(userProcessingService);
        verifyNoInteractions(userRepositoryService);
    }

    @Test
    void testGetUserWithNullLogin() {
        String login = null;
        assertThrows(IllegalArgumentException.class, () -> userService.getUser(login));
        verifyNoInteractions(githubApiService);
        verifyNoInteractions(userProcessingService);
        verifyNoInteractions(userRepositoryService);
    }

    @Test
    void testGetUserWithRestClientException() {
        String login = "testuser";

        when(githubApiService.fetchUserData(login)).thenThrow(new RestClientException("API Error"));

        Optional<User> result = userService.getUser(login);

        assertFalse(result.isPresent());
        verifyNoInteractions(userProcessingService);
        verifyNoInteractions(userRepositoryService);
    }
}