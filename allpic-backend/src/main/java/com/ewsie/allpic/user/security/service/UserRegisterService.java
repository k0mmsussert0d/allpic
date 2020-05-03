package com.ewsie.allpic.user.security.service;

import org.springframework.http.ResponseEntity;

public interface UserRegisterService {

    ResponseEntity<String> register(String username, String password, String email);
}
