package com.ewsie.allpic.user.security.service;

import com.ewsie.allpic.user.model.AuthenticatedUserDTO;
import com.ewsie.allpic.user.model.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

public interface UserLoginService {

    ResponseEntity<AuthenticatedUserDTO> login(String username, String password);

    ResponseEntity<String> authenticate(@AuthenticationPrincipal CustomUserDetails user);
}
