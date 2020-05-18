package com.budget.io.authserviceio.service;

import com.budget.io.authserviceio.model.User;

public interface IUserService {
    User save(User user);
    User getById(Long id);
    User getByUsername(String username);
}
