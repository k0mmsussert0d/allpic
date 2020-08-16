package com.ewsie.allpic.user.security.controller;

import com.ewsie.allpic.user.model.AuthenticatedUserDTO;
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
import springfox.documentation.annotations.ApiIgnore;


@RequestMapping("/auth")
public interface UserAuthController {

    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody UserLoginRequestBody requestBody);

    @PreAuthorize("isFullyAuthenticated()")
    @GetMapping("/")
    ResponseEntity<AuthenticatedUserDTO> authenticate(@AuthenticationPrincipal @ApiIgnore CustomUserDetails user);
}
