package com.ewsie.allpic.image.controller.impl;

import com.ewsie.allpic.image.controller.ImageController;
import com.ewsie.allpic.image.model.ImageDTO;
import com.ewsie.allpic.image.service.SaveImageService;
import com.ewsie.allpic.user.session.model.SessionDTO;
import com.ewsie.allpic.user.session.service.SessionDTOService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ImageControllerImpl implements ImageController {

    private final SessionDTOService sessionDTOService;
    private final SaveImageService saveImageService;

    @Override
    public ResponseEntity<String> getImage(String id) {
        return null;
    }

    @Override
    public ResponseEntity<String> uploadImage(MultipartFile image, String sessionId) {
        Optional<SessionDTO> uploaderSession = Optional.empty();

        if (!StringUtils.isEmpty(sessionId)) {
            uploaderSession = Optional.ofNullable(sessionDTOService.findByIdentifier(sessionId));

            if (uploaderSession.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid session identifier");
            }
        }

        try {
            ImageDTO savedImage = saveImageService.saveImage(image, uploaderSession.orElse(null));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
