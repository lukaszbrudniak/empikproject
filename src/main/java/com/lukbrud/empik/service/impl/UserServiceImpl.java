package com.lukbrud.empik.service.impl;

import com.lukbrud.empik.model.GithubUser;
import com.lukbrud.empik.model.User;
import com.lukbrud.empik.service.GithubApiService;
import com.lukbrud.empik.service.UserProcessingService;
import com.lukbrud.empik.service.UserRepositoryService;
import com.lukbrud.empik.service.UserService;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.Optional;


/**
 * Service implementation for user-related operations.
 * Fetches user data from GitHub, processes it, and manages request counts.
 *
 * @author Lukasz Brudniak
 */
@Service
@Slf4j
class UserServiceImpl implements UserService {
    private final GithubApiService githubApiService;
    private final UserProcessingService userProcessingService;
    private final UserRepositoryService userRepositoryService;

    @Autowired
    public UserServiceImpl(
            GithubApiService githubApiService,
            UserProcessingService userProcessingService,
            UserRepositoryService userRepositoryService) {
        this.githubApiService = githubApiService;
        this.userProcessingService = userProcessingService;
        this.userRepositoryService = userRepositoryService;
    }

    /**
     * Retrieves user data by login.
     *
     * @param login The login of the user to retrieve.
     * @return Optional containing User object if found, or empty if not found.
     */

    @Override
    @Transactional
    public Optional<User> getUser(String login) {
        validateLogin(login);

        try {
            GithubUser githubUser = githubApiService.fetchUserData(login);
            User user = userProcessingService.processUserData(login, githubUser);

            if (user != null) {
                userRepositoryService.incrementRequestCount(login);
            }

            return Optional.ofNullable(user);
        } catch (RestClientException e) {
            log.warn("Error fetching user data for login: {}", login, e);
            return Optional.empty();
        }
    }

    private void validateLogin(String login) {
        if (StringUtils.isBlank(login)) {
            throw new IllegalArgumentException("Login cannot be null or empty.");
        }
    }
}