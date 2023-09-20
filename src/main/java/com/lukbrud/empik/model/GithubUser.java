package com.lukbrud.empik.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GithubUser {
    private Integer id;
    private String login;
    private String name;
    private String type;
    private String avatar_url;
    private LocalDateTime created_at;
    private Integer followers;
    private Integer public_repos;
}