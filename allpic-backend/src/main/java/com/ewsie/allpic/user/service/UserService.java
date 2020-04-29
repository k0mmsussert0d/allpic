package com.ewsie.allpic.user.service;

import com.ewsie.allpic.user.model.User;

public interface UserService {
    void create(User user);

    User findById(Long id);

    User findByUsername(String username);

    User findByEmail(String email);
}
