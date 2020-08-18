package com.ewsie.allpic.user.security.controller;

import com.ewsie.allpic.user.model.UserDTO;
import com.ewsie.allpic.user.model.request.UserRegisterRequestBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags = {"User authentication"})
@RequestMapping("/register")
public interface UserRegisterController {

    @ApiOperation(value = "Registers new user")
    @PostMapping("/")
    ResponseEntity<UserDTO> register(@RequestBody UserRegisterRequestBody requestBody);
}
