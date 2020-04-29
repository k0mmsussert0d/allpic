package com.ewsie.allpic.user.controller.impl;

import com.ewsie.allpic.config.TokenService;
import com.ewsie.allpic.session.model.SessionDTO;
import com.ewsie.allpic.session.service.SessionDTOService;
import com.ewsie.allpic.user.controller.UserAuth;
import com.ewsie.allpic.user.model.UserDTO;
import com.ewsie.allpic.user.service.UserDTOService;
import com.ewsie.allpic.user.service.impl.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserAuthImpl implements UserAuth {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final SessionDTOService sessionDTOService;
    private final UserDTOService userDTOService;

    @Override
    public List<String> helloWorld() {
        return Arrays.asList("hello", "world");
    }

    @Override
    public ResponseEntity<String> helloSecured() {
        return new ResponseEntity<>("hello secured", HttpStatus.OK);
    }

    @Override
    public List<String> userInfo(HttpSession session) {
        return Arrays.asList("user", "info", "dupa");
    }

    @Override
    public ResponseEntity<String> login(String username, String password) {

        UserDetails requestedUserAccessDetails = userDetailsService.loadUserByUsername(username);
        UserDTO requestedUserDTO = userDTOService.findByUsername(username);

        if (requestedUserAccessDetails != null) {
            boolean passwordMatches = passwordEncoder.matches(password, requestedUserAccessDetails.getPassword());

            if (passwordMatches && requestedUserAccessDetails.isEnabled()) {
                String sessionId = tokenService.createToken();

                SessionDTO newSession = SessionDTO.builder()
                        .sessionIdentifier(sessionId)
                        .user(requestedUserDTO)
                        .validUntil(LocalDateTime.now().plusHours(3)) // TODO: parametrize this variable
                        .build();
                sessionDTOService.create(newSession);

                ResponseCookie cookie = ResponseCookie
                        .from("authentication", sessionId)
                        .maxAge(Duration.ofDays(30)) // TODO: parametrize this variable
                        .sameSite("Strict")
                        .path("/")
                        .httpOnly(true)
                        .secure(false) // TODO: parametrize this variable
                        .build();

                return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                        .body("Authorized " + requestedUserDTO.getUsername());
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
