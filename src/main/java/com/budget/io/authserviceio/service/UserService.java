package com.budget.io.authserviceio.service;

import com.budget.io.authserviceio.model.User;
import com.budget.io.authserviceio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService{

    @Autowired
    public UserRepository userRepository;

    @Override
    public User save(User user){
        return userRepository.save(user);
    }

    @Override
    public User getById(Long id){
        return userRepository.getOne(id);
    }

    @Override
    public Optional<User> getByUsername(String username){
        return userRepository.findByUsername(username);
    }

}
