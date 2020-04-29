package com.ewsie.allpic.user.service.impl;

import com.ewsie.allpic.user.model.CustomUserDetails;
import com.ewsie.allpic.user.model.User;
import com.ewsie.allpic.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = Optional.ofNullable(userService.findByUsername(username));

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }

        return new CustomUserDetails(user.get());
    }
}
