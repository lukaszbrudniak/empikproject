package com.lukbrud.empik.service.impl;

import com.lukbrud.empik.model.GithubUser;
import com.lukbrud.empik.model.User;
import com.lukbrud.empik.service.UserCalculationService;
import com.lukbrud.empik.service.UserProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Service implementation for processing user data.
 * Calculates user-specific values and prepares User objects.
 *
 * @author Lukasz Brudniak
 */
@Service
class UserProcessingServiceImpl implements UserProcessingService {
    private final UserCalculationService userCalculationService;

    @Autowired
    public UserProcessingServiceImpl(UserCalculationService userCalculationService) {
        this.userCalculationService = userCalculationService;
    }

    /**
     * Process GitHub user data and calculate user-specific values.
     *
     * @param login       The login of the user.
     * @param githubUser  GitHubUser object containing user data.
     * @return User object with processed data.
     */
    @Override
    public User processUserData(String login, GithubUser githubUser) {
        if (githubUser == null) {
            return null;
        }

        double calculations = userCalculationService.calculateUserCalculations(githubUser.getFollowers(), githubUser.getPublic_repos());

        return User.builder()
                .id(githubUser.getId())
                .login(githubUser.getLogin())
                .name(githubUser.getName())
                .type(githubUser.getType())
                .avatarUrl(githubUser.getAvatar_url())
                .createdAt(githubUser.getCreated_at())
                .calculations(calculations)
                .build();
    }
}