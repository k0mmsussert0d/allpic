package com.ewsie.allpic.user.security.controller;

import com.ewsie.allpic.user.model.UserDTO;
import com.ewsie.allpic.user.model.request.UserRegisterRequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/register")
public interface UserRegisterController {

    @PostMapping("/")
    ResponseEntity<UserDTO> register(@RequestBody UserRegisterRequestBody requestBody);
}
