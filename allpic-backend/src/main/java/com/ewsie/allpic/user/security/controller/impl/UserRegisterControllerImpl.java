package com.ewsie.allpic.user.security.controller.impl;

import com.ewsie.allpic.user.model.UserDTO;
import com.ewsie.allpic.user.model.request.UserRegisterRequestBody;
import com.ewsie.allpic.user.security.controller.UserRegisterController;
import com.ewsie.allpic.user.security.service.UserRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRegisterControllerImpl implements UserRegisterController {

    private final UserRegisterService userRegisterService;

    @Override
    public ResponseEntity<UserDTO> register(UserRegisterRequestBody requestBody) {
        return userRegisterService.register(
                requestBody.getUsername(),
                requestBody.getPassword(),
                requestBody.getEmail()
        );
    }
}
