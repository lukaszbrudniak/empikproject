package com.lukbrud.empik.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "USER_REQUEST",
        indexes = {@Index(name = "idx_login", columnList = "LOGIN")}
)
@Getter
@Setter
public class UserEntity {

    @Id
    @Column(name = "LOGIN")
    private String login;

    @Column(name = "REQUEST_COUNT")
    private int requestCount;
}