package com.example.service;

import com.example.model.User;
import com.thoughtworks.di.annotation.Component;

@Component
public class UserServiceImpl implements UserService {
    @Override
    public User get(Long id) {
        return new User(id, "default user", 0);
    }
}
