package com.ewsie.allpic.user.security.service.impl;

import com.ewsie.allpic.config.AppConfig;
import com.ewsie.allpic.user.model.AuthenticatedUserDTO;
import com.ewsie.allpic.user.model.CustomUserDetails;
import com.ewsie.allpic.user.model.UserDTO;
import com.ewsie.allpic.user.security.service.UserLoginService;
import com.ewsie.allpic.user.service.UserDTOService;
import com.ewsie.allpic.user.session.SessionTokenService;
import com.ewsie.allpic.user.session.model.SessionDTO;
import com.ewsie.allpic.user.session.service.SessionDTOService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {

    private final UserDTOService userDTOService;
    private final PasswordEncoder passwordEncoder;
    private final SessionTokenService sessionTokenService;
    private final SessionDTOService sessionDTOService;
    private final AppConfig appConfig;

    @Override
    public ResponseEntity<AuthenticatedUserDTO> login(String username, String password) {

        Optional<UserDTO> requestedUser = Optional.ofNullable(userDTOService.findByUsername(username));

        if (requestedUser.isPresent()) {
            boolean passwordMatches = passwordEncoder.matches(password, requestedUser.get().getPassword());

            if (passwordMatches && requestedUser.get().getIsActive()) {
                String sessionId = sessionTokenService.createToken();

                SessionDTO newSession = SessionDTO.builder()
                        .sessionIdentifier(sessionId)
                        .user(requestedUser.get())
                        .validUntil(LocalDateTime.now().plusHours(3))
                        .build();
                sessionDTOService.create(newSession);

                ResponseCookie cookie = ResponseCookie
                        .from("authentication", sessionId)
                        .maxAge(Duration.ofSeconds(appConfig.getCookieMaxAge()))
                        .sameSite("Strict")
                        .path("/")
                        .httpOnly(true)
                        .secure(appConfig.isCookieSecure())
                        .build();

                return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                        .body(
                                AuthenticatedUserDTO.builder()
                                .username(requestedUser.get().getUsername())
                                .role(requestedUser.get().getRole().getRoleName())
                                .build()
                        );
            }
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<String> authenticate(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok().body(user.getUsername());
    }
}
