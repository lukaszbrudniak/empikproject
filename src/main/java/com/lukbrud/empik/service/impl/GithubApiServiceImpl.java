package com.lukbrud.empik.service.impl;

import com.lukbrud.empik.model.GithubUser;
import com.lukbrud.empik.service.GithubApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service implementation for fetching data from the GitHub API.
 * Caches responses for improved performance.
 *
 * @author Lukasz Brudniak
 */

@Service
class GithubApiServiceImpl implements GithubApiService {
    private static final String GITHUB_API_BASE_URL = "https://api.github.com/users/";

    private final RestTemplate restTemplate;

    @Autowired
    public GithubApiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Fetches user data from the GitHub API by login.
     *
     * @param login The login of the user to fetch.
     * @return GitHubUser object containing user data.
     */

    @Cacheable(value = "userCache", key = "#login", cacheManager = "cacheManager")
    public GithubUser fetchUserData(String login) {
        String githubApiUrl = GITHUB_API_BASE_URL + login;
        return restTemplate.getForObject(githubApiUrl, GithubUser.class);
    }
}