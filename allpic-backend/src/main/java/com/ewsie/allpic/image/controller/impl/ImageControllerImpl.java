package com.ewsie.allpic.image.controller.impl;

import com.ewsie.allpic.image.controller.ImageController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ImageControllerImpl implements ImageController {


    @Override
    public ResponseEntity<String> getImage(String id) {
        return null;
    }

    @Override
    public ResponseEntity<String> uploadImage(MultipartFile image, String sessionId) {

        return null;
    }
}
