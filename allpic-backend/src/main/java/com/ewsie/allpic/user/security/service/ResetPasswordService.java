package com.ewsie.allpic.user.security.service;

import com.ewsie.allpic.user.model.UserDTO;

public interface ResetPasswordService {

    UserDTO resetPassword(UserDTO userDTO, String oldPwd, String newPwd);
}
