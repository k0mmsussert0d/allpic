package com.ewsie.allpic.image.controller;

import com.ewsie.allpic.image.model.ImageDTO;
import com.ewsie.allpic.image.model.ImageDetails;
import com.ewsie.allpic.user.model.CustomUserDetails;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/img")
public interface ImageController {

    @GetMapping("/i/{token}")
    ResponseEntity<Resource> getImage(@PathVariable String token);

    @PostMapping("/upload")
    ResponseEntity<String> uploadImage(
            @RequestPart("file") MultipartFile image,
            @RequestPart("metadata") ImageDetails imageDetails,
            @AuthenticationPrincipal @Nullable CustomUserDetails user
    );

    @DeleteMapping("/i/{token}")
    @PreAuthorize("hasAnyRole('ROLE_MOD','ROLE_ADMIN')")
    ResponseEntity<ImageDTO> removeImage(@PathVariable String token);
}
