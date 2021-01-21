package com.ewsie.allpic.image.controller.impl;

import com.ewsie.allpic.image.controller.AvatarController;
import com.ewsie.allpic.user.avatar.service.SaveAvatarService;
import com.ewsie.allpic.user.model.CustomUserDetails;
import com.ewsie.allpic.user.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class AvatarControllerImpl implements AvatarController {

    private final SaveAvatarService saveAvatarService;

    @Override
    public ResponseEntity<UserDTO> updateAvatar(CustomUserDetails userDetails, MultipartFile file) {
        try {
            saveAvatarService.saveAvatar(file.getInputStream(), userDetails.getUser());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }

        return ResponseEntity.ok(userDetails.getUser());
    }

    @Override
    public ResponseEntity<UserDTO> deleteAvatar() {
        return null;
    }
}
