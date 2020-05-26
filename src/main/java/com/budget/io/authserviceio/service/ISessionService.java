package com.budget.io.authserviceio.service;

import com.budget.io.authserviceio.model.SessionJedis;

import java.util.Optional;

public interface ISessionService {
    SessionJedis save(SessionJedis user);
    SessionJedis findByUid(Long id);
}
