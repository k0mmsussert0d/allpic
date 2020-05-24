package com.ewsie.allpic.user.security.controller;

import com.ewsie.allpic.user.model.CustomUserDetails;
import com.ewsie.allpic.user.model.request.UserLoginRequestBody;
import com.ewsie.allpic.user.model.request.UserRegisterRequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/auth")
public interface UserAuth {

    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody UserLoginRequestBody requestBody);

    @PreAuthorize("isFullyAuthenticated()")
    @GetMapping("/authenticate")
    ResponseEntity<String> authenticate(@AuthenticationPrincipal CustomUserDetails user);

    @PostMapping("/register")
    ResponseEntity<String> register(@RequestBody UserRegisterRequestBody requestBody);
}
