package com.ewsie.allpic.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/auth")
public interface UserAuth {

    @GetMapping("/hello")
    List<String> helloWorld();

    @GetMapping("/hello/secured")
    ResponseEntity<String> helloSecured();

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/user")
    List<String> userInfo(HttpSession session);

    @PostMapping("/login")
    ResponseEntity<String> login(String username, String password);
}
