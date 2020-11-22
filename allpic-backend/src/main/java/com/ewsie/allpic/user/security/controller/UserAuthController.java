package com.ewsie.allpic.user.security.controller;

import com.ewsie.allpic.user.model.AuthenticatedUserDTO;
import com.ewsie.allpic.user.model.CustomUserDetails;
import com.ewsie.allpic.user.model.request.UserLoginRequestBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags={"User authentication"})
@RequestMapping("/auth")
public interface UserAuthController {

    @ApiOperation(value = "Authenticates the user with username and password")
    @PostMapping("/login")
    ResponseEntity<AuthenticatedUserDTO> login(@RequestBody UserLoginRequestBody requestBody);

    @ApiOperation(value = "Authenticates the user with session ID stored in 'authentication' cookie")
    @PreAuthorize("isFullyAuthenticated()")
    @GetMapping("/")
    ResponseEntity<AuthenticatedUserDTO> authenticate(@AuthenticationPrincipal @ApiIgnore CustomUserDetails user);
}
