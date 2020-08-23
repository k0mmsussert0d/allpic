package com.ewsie.allpic.user.security.service.impl;

import com.ewsie.allpic.user.model.UserDTO;
import com.ewsie.allpic.user.role.RoleDTO;
import com.ewsie.allpic.user.role.service.RoleDTOService;
import com.ewsie.allpic.user.security.service.UserRegisterService;
import com.ewsie.allpic.user.service.UserDTOService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRegisterServiceImpl implements UserRegisterService {

    private final UserDTOService userDTOService;
    private final RoleDTOService roleDTOService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<UserDTO> register(String username, String password, String email) {
        if (!isUsernameAvailable(username)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Username " + username + " is already in use"
            );
        } else if (!isEmailNotUsed(email)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Username with e-mail address " + email + " is already registered"
            );
        } else if (!isPasswordValid(password)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Password does not meet requirements"
            );
        }

        RoleDTO defaultRole = roleDTOService.findByRoleName("USER");

        UserDTO newUser = UserDTO.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .registerTime(LocalDateTime.now())
                .isActive(true)
                .role(defaultRole)
                .build();

        return ResponseEntity.ok(userDTOService.create(newUser));
    }

    private boolean isUsernameAvailable(String username) {
        Optional<UserDTO> userDTO = Optional.ofNullable(userDTOService.findByUsername(username));

        return userDTO.isEmpty();
    }

    private boolean isEmailNotUsed(String email) {
        Optional<UserDTO> userDTO = Optional.ofNullable(userDTOService.findByEmail(email));

        return userDTO.isEmpty();
    }

    private boolean isPasswordValid(String password) {
        return true; // TODO: add password complexity checker
    }
}
