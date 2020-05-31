package com.budget.io.authserviceio.service;

import com.budget.io.authserviceio.model.User;

import java.util.Optional;

public interface IUserService {
    User save(User user);
    User getById(Long id);
    Optional<?> getByUsername(String username);
}
