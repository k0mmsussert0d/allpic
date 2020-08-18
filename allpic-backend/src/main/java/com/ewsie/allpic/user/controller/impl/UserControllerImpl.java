package com.ewsie.allpic.user.controller.impl;

import com.ewsie.allpic.image.model.ImageDTO;
import com.ewsie.allpic.image.service.ImageDTOService;
import com.ewsie.allpic.user.controller.UserController;
import com.ewsie.allpic.user.model.CustomUserDetails;
import com.ewsie.allpic.user.model.UserDTO;
import com.ewsie.allpic.user.service.UserDTOService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserDTOService userDTOService;
    private final ImageDTOService imageDTOService;

    @Override
    public ResponseEntity<List<UserDTO>> users() {
        Optional<List<UserDTO>> users = Optional.ofNullable(userDTOService.getAll());
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(users.get());
    }

    @Override
    public ResponseEntity<UserDTO> userInfo(String username) {
        Optional<UserDTO> requestedUser = Optional.ofNullable(userDTOService.findByUsername(username));
        if (requestedUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(requestedUser.get());
    }

    @Override
    public ResponseEntity<List<ImageDTO>> userImages(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UserDTO user = userDetails.getUser();

        Optional<List<ImageDTO>> images = Optional.ofNullable(imageDTOService.findAllUploadedBy(user));
        if (images.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(Collections.emptyList());
        }

        return ResponseEntity.status(HttpStatus.OK).body(images.get());
    }
}
