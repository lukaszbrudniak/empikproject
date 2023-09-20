package com.lukbrud.empik.service.impl;

import com.lukbrud.empik.model.GithubUser;
import com.lukbrud.empik.service.GithubApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
        return restTemplate.getForObject(githubApiUrl, GithubUser.class);
    }
}