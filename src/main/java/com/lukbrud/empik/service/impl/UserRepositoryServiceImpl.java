package com.lukbrud.empik.service.impl;

import com.lukbrud.empik.entity.UserEntity;
import com.lukbrud.empik.repository.UserRepository;
import com.lukbrud.empik.service.UserRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class UserRepositoryServiceImpl implements UserRepositoryService {
    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void incrementRequestCount(String login) {
        UserEntity userEntity = userRepository.findById(login).orElseGet(() -> {
            UserEntity newUserEntity = new UserEntity();
            newUserEntity.setLogin(login);
            newUserEntity.setRequestCount(0);
            return newUserEntity;
        });

        userEntity.setRequestCount(userEntity.getRequestCount() + 1);
        userRepository.save(userEntity);
    }
}