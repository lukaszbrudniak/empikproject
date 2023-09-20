package com.lukbrud.empik.service.impl;

import com.lukbrud.empik.exception.ResourceNotFoundException;
import com.lukbrud.empik.model.GithubUser;
import com.lukbrud.empik.service.GithubApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
class GithubApiServiceImpl implements GithubApiService {
    private static final String GITHUB_API_BASE_URL = "https://api.github.com/users/";

    private final RestTemplate restTemplate;

    @Autowired
    public GithubApiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable(value = "userCache", key = "#login", cacheManager = "cacheManager")
    public GithubUser fetchUserData(String login) {
        String githubApiUrl = GITHUB_API_BASE_URL + login;

        try {
            GithubUser user = restTemplate.getForObject(githubApiUrl, GithubUser.class);
            log.info("Fetched user: " + login);
            return user;
        } catch (HttpClientErrorException.NotFound e) {
            log.warn("User not found: {}", login);
            throw new ResourceNotFoundException("User not found: " + login);
        } catch (RuntimeException e) {
            log.error("An error occurred while fetching GitHub data: {}", e.getMessage());
            throw new RuntimeException("An error occurred while fetching GitHub data: " + e.getMessage());
        }
}}