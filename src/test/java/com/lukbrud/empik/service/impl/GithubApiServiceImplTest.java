package com.lukbrud.empik.service.impl;

import com.lukbrud.empik.model.GithubUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GithubApiServiceImplTest {

    private GithubApiServiceImpl githubApiService;

    @Mock
    private RestTemplate restTemplate;

    private PodamFactory podamFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        githubApiService = new GithubApiServiceImpl(restTemplate);
        podamFactory = new PodamFactoryImpl();
    }

    @Test
    void testFetchUserData() {
        String login = "testUser";
        String apiUrl = "https://api.github.com/users/" + login;
        GithubUser expectedUser = podamFactory.manufacturePojo(GithubUser.class);

        when(restTemplate.getForObject(apiUrl, GithubUser.class)).thenReturn(expectedUser);

        GithubUser actualUser = githubApiService.fetchUserData(login);

        verify(restTemplate, times(1)).getForObject(apiUrl, GithubUser.class);

        assertEquals(expectedUser, actualUser);
    }
}