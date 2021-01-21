package com.ewsie.allpic.user.security.service.impl;

import com.ewsie.allpic.user.model.UserDTO;
import com.ewsie.allpic.user.security.service.ResetPasswordService;
import com.ewsie.allpic.user.service.UserDTOService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResetPasswordServiceImpl implements ResetPasswordService {

    private final PasswordEncoder passwordEncoder;
    private final UserDTOService userDTOService;

    @Override
    public UserDTO resetPassword(UserDTO userDTO, String oldPwd, String newPwd) {
        boolean passwordMatches = passwordEncoder.matches(oldPwd, userDTO.getPassword());
        if (!passwordMatches) {
            throw new AssertionError("Old password is incorrect");
        }

        userDTO.setPassword(passwordEncoder.encode(newPwd));
        return userDTOService.create(userDTO);
    }
}
