package com.lukbrud.empik.service;

import com.lukbrud.empik.model.GithubUser;

public interface GithubApiService {
    GithubUser fetchUserData(String login);
}
