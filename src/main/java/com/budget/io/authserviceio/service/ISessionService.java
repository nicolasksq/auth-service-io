package com.budget.io.authserviceio.service;

import com.budget.io.authserviceio.model.Session;

public interface ISessionService {
    Session save(Session user);
    Session findByUid(Long id);
}
