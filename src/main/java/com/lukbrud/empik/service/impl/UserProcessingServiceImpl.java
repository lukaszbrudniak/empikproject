package com.lukbrud.empik.service.impl;

import com.lukbrud.empik.model.GithubUser;
import com.lukbrud.empik.model.User;
import com.lukbrud.empik.service.UserCalculationService;
import com.lukbrud.empik.service.UserProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class UserProcessingServiceImpl implements UserProcessingService {
    private final UserCalculationService userCalculationService;

    @Autowired
    public UserProcessingServiceImpl(UserCalculationService userCalculationService) {
        this.userCalculationService = userCalculationService;
    }

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