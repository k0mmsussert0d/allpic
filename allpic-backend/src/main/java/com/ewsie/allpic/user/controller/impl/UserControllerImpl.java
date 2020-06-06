package com.ewsie.allpic.user.controller.impl;

import com.ewsie.allpic.image.model.ImageDTO;
import com.ewsie.allpic.image.service.ImageDTOService;
import com.ewsie.allpic.user.model.UserDTO;
import com.ewsie.allpic.user.service.UserDTOService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserDTOService userDTOService;
    private final ImageDTOService imageDTOService;

    @Override
    public ResponseEntity<UserDTO> userInfo(String username) {
        Optional<UserDTO> requestedUser = Optional.ofNullable(userDTOService.findByUsername(username));
        if (requestedUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(requestedUser.get());
    }

    @Override
    public ResponseEntity<List<ImageDTO>> userImages(String username) {
        Optional<UserDTO> requestedUser = Optional.ofNullable(userDTOService.findByUsername(username));
        if (requestedUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<ImageDTO> images = imageDTOService.findAllUploadedBy(requestedUser.get()).stream()
                .filter(i -> i.getIsPublic() && i.getIsActive())
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(images);
    }
}
