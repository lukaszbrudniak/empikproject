package com.lukbrud.empik.service.impl;

import com.lukbrud.empik.model.GithubUser;

public interface GithubApiService {
    GithubUser fetchUserData(String login);
}
