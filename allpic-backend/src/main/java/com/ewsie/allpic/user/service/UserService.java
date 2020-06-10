package com.ewsie.allpic.user.service;

import com.ewsie.allpic.user.model.User;
import com.ewsie.allpic.user.role.Role;

import java.util.List;

public interface UserService {
    void create(User user);

    User findById(Long id);

    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findUsersByRole(Role role);
}
