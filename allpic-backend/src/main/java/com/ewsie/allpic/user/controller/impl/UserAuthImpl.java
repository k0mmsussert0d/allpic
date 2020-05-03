package com.ewsie.allpic.user.controller.impl;

import com.ewsie.allpic.user.security.service.UserLoginService;
import com.ewsie.allpic.user.controller.UserAuth;
import com.ewsie.allpic.user.model.CustomUserDetails;
import com.ewsie.allpic.user.model.request.UserLoginRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class UserAuthImpl implements UserAuth {

    private final UserLoginService userLoginService;

    @Override
    public ResponseEntity<String> login(@RequestBody UserLoginRequestBody requestBody) {

        String username = requestBody.getUsername();
        String password = requestBody.getPassword();

        return userLoginService.login(username, password);
    }

    @Override
    public ResponseEntity<String> authenticate(@AuthenticationPrincipal CustomUserDetails user) {
        return userLoginService.authenticate(user);
    }
}
