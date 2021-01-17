package com.ewsie.allpic.user.avatar.service;

import com.ewsie.allpic.user.model.UserDTO;

import java.io.IOException;
import java.io.InputStream;

public interface SaveAvatarService {

    void saveAvatar(InputStream avatarFileInputStream, UserDTO user) throws IOException;
}
