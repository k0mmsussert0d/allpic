package com.ewsie.allpic.user.security.service;

import com.ewsie.allpic.user.model.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserRegisterService {

    ResponseEntity<UserDTO> register(String username, String password, String email);
}
