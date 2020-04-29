package com.ewsie.allpic.user.service.impl;

import com.ewsie.allpic.user.model.User;
import com.ewsie.allpic.user.repository.UserRepository;
import com.ewsie.allpic.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void create(User user) {
        userRepository.save(user);
    }

    @Override
    @Nullable
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Nullable
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    @Nullable
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
