package com.ewsie.allpic.user.avatar.service;

import com.ewsie.allpic.user.avatar.model.AvatarContent;
import com.ewsie.allpic.user.model.UserDTO;

import java.io.InputStream;

public interface LoadAvatarService {

    AvatarContent loadAvatar(UserDTO userDTO);
}
