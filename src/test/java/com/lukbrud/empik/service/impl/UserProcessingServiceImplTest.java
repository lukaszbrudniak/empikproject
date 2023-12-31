package com.lukbrud.empik.service.impl;

import com.lukbrud.empik.model.GithubUser;
import com.lukbrud.empik.model.User;
import com.lukbrud.empik.service.UserCalculationService;
import com.lukbrud.empik.service.UserProcessingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserProcessingServiceImplTest {

    private UserProcessingService userProcessingService;

    @Mock
    private UserCalculationService userCalculationService;

    private PodamFactory podamFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userProcessingService = new UserProcessingServiceImpl(userCalculationService);
        podamFactory = new PodamFactoryImpl();
    }

    @Test
    void shouldProcessUserDataWithNonNullGithubUser() {
        // Given
        GithubUser githubUser = podamFactory.manufacturePojo(GithubUser.class);
        githubUser.setFollowers(10);

        when(userCalculationService.calculateUserCalculations(10, githubUser.getPublic_repos()))
                .thenReturn(0.5);

        // When
        User processedUser = userProcessingService.processUserData("testuser", githubUser);

        // Then
        assertNotNull(processedUser);
        assertEquals(0.5, processedUser.getCalculations());
    }

    @Test
    void shouldProcessUserDataWithNullGithubUser() {
        // When
        User processedUser = userProcessingService.processUserData("testuser", null);

        // Then
        assertNull(processedUser);
    }

    @Test
    void shouldProcessUserDataWithZeroFollowers() {
        // Given
        GithubUser githubUser = podamFactory.manufacturePojo(GithubUser.class);
        githubUser.setFollowers(0);

        when(userCalculationService.calculateUserCalculations(0, githubUser.getPublic_repos()))
                .thenReturn(0.0);

        // When
        User processedUser = userProcessingService.processUserData("testuser", githubUser);

        // Then
        assertNotNull(processedUser);
        assertEquals(0.0, processedUser.getCalculations());
    }
}