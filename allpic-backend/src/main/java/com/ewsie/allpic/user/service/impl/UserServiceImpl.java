package com.ewsie.allpic.user.service.impl;

import com.ewsie.allpic.user.model.User;
import com.ewsie.allpic.user.repository.UserRepository;
import com.ewsie.allpic.user.role.Role;
import com.ewsie.allpic.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
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

    @Override
    public List<User> findUsersByRole(Role role) {
        return userRepository.findUsersByRole(role).orElse(null);
    }
}
