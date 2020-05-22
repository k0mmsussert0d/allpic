package com.ewsie.allpic.image.controller.impl;

import com.amazonaws.SdkClientException;
import com.ewsie.allpic.image.controller.ImageController;
import com.ewsie.allpic.image.model.ImageDTO;
import com.ewsie.allpic.image.model.ImageDetails;
import com.ewsie.allpic.image.service.SaveImageService;
import com.ewsie.allpic.user.model.CustomUserDetails;
import com.ewsie.allpic.user.model.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ImageControllerImpl implements ImageController {

    private final SaveImageService saveImageService;

    @Override
    public ResponseEntity<String> getImage(String id) {
        return null;
    }

    @Override
    public ResponseEntity<String> uploadImage(MultipartFile image, ImageDetails imageDetails, CustomUserDetails user) {
        String title = imageDetails.getTitle();
        boolean isPublic = imageDetails.isPublic();

        Optional<CustomUserDetails> uploaderDetails = Optional.ofNullable(user);
        UserDTO uploaderDto = null;
        if (uploaderDetails.isPresent()) {
            uploaderDto = uploaderDetails.get().getUser();
        }

        String response;
        try {
            ImageDTO savedImage = saveImageService.saveImage(image, title, isPublic, uploaderDto);
            response = new ObjectMapper().writeValueAsString(savedImage);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (SdkClientException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }

        return ResponseEntity.ok(response);
    }
}
