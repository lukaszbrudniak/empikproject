package com.lukbrud.empik.service;

import com.lukbrud.empik.model.GithubUser;
import com.lukbrud.empik.model.User;

public interface UserProcessingService {
    User processUserData(String login, GithubUser githubUser);
}
