package com.ewsie.allpic.user.security.controller.impl;

import com.ewsie.allpic.user.model.AuthenticatedUserDTO;
import com.ewsie.allpic.user.security.service.UserLoginService;
import com.ewsie.allpic.user.security.controller.UserAuthController;
import com.ewsie.allpic.user.model.CustomUserDetails;
import com.ewsie.allpic.user.model.request.UserLoginRequestBody;
import com.ewsie.allpic.user.security.service.UserRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class UserAuthControllerImpl implements UserAuthController {

    private final UserLoginService userLoginService;

    @Override
    public ResponseEntity<AuthenticatedUserDTO> login(@RequestBody UserLoginRequestBody requestBody) {

        String username = requestBody.getUsername();
        String password = requestBody.getPassword();

        return userLoginService.login(username, password);
    }

    @Override
    public ResponseEntity<AuthenticatedUserDTO> authenticate(@AuthenticationPrincipal CustomUserDetails user) {
        String roles = user.getAuthorities().toString().replaceAll("([\\[\\]])", ""); // remove [ and ]

        return ResponseEntity.ok(
                AuthenticatedUserDTO.builder()
                        .username(user.getUsername())
                        .role(roles)
                        .build()
        );
    }
}
