package com.ewsie.allpic.image.controller;

import com.ewsie.allpic.image.model.ImageDetails;
import com.ewsie.allpic.user.model.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/img")
public interface ImageController {

    @GetMapping("/i/{id}")
    ResponseEntity<String> getImage(@PathVariable String id);

    @PostMapping("/upload")
    ResponseEntity<String> uploadImage(
            @RequestPart("file") MultipartFile image,
            @RequestPart("metadata") ImageDetails imageDetails,
            @AuthenticationPrincipal @Nullable CustomUserDetails user
    );
}
