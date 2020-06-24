package com.ewsie.allpic.image.controller.impl;

import com.amazonaws.SdkClientException;
import com.ewsie.allpic.image.controller.ImageController;
import com.ewsie.allpic.image.model.ImageDTO;
import com.ewsie.allpic.image.model.ImageDTOWithContent;
import com.ewsie.allpic.image.model.ImagePreviewDetails;
import com.ewsie.allpic.image.model.UploadImageDetails;
import com.ewsie.allpic.image.service.ImageDTOService;
import com.ewsie.allpic.image.service.LoadImageService;
import com.ewsie.allpic.image.service.SaveImageService;
import com.ewsie.allpic.image.service.UnpublishImageService;
import com.ewsie.allpic.user.model.CustomUserDetails;
import com.ewsie.allpic.user.model.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ImageControllerImpl implements ImageController {

    private final ImageDTOService imageDTOService;
    private final LoadImageService loadImageService;
    private final SaveImageService saveImageService;
    private final UnpublishImageService unpublishImageService;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<Resource> getImage(String token) {
        ImageDTOWithContent requestedImage;

        try {
            requestedImage = loadImageService.loadImage(token);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (SdkClientException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }

        return ResponseEntity.ok()
                .contentLength(requestedImage.getContentLength())
                .contentType(MediaType.parseMediaType(requestedImage.getContentType()))
                .body(new InputStreamResource(requestedImage.getContent()));
    }

    @Override
    public ResponseEntity<Resource> getImageThumbnail(String token) {
        ImageDTOWithContent requestedImage;

        try {
            requestedImage = loadImageService.loadThumb(token);
        } catch (SdkClientException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok()
                .contentLength(requestedImage.getContentLength())
                .contentType(MediaType.parseMediaType(requestedImage.getContentType()))
                .body(new InputStreamResource(requestedImage.getContent()));
    }

    @Override
    public ResponseEntity<String> uploadImage(MultipartFile image, UploadImageDetails imageDetails, CustomUserDetails user) {
        String title = imageDetails.getTitle();
        boolean isPublic = imageDetails.isPublic;

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

    @Override
    public ResponseEntity<List<ImagePreviewDetails>> getMostRecentImages() {
        List<ImageDTO> recentImages = imageDTOService.findAllOrderByUploadedTimeDesc();

        List<ImagePreviewDetails> res = recentImages.stream()
                .map(i -> modelMapper.map(i, ImagePreviewDetails.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(res);
    }

    @Override
    public ResponseEntity<Void> removeImage(String token) {
        try {
            unpublishImageService.hideImageByToken(token);
        } catch(NullPointerException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
