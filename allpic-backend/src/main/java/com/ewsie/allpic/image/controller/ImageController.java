package com.ewsie.allpic.image.controller;

import com.ewsie.allpic.image.model.ImagePreviewDetails;
import com.ewsie.allpic.image.model.UploadImageDetails;
import com.ewsie.allpic.user.model.CustomUserDetails;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RequestMapping("/img")
public interface ImageController {

    @GetMapping("/i/{token}")
    ResponseEntity<Resource> getImage(@PathVariable String token);

    @GetMapping("/i/thumb/{token}")
    ResponseEntity<Resource> getImageThumbnail(@PathVariable String token);

    @PostMapping("/upload")
    ResponseEntity<String> uploadImage(
            @RequestPart("file") MultipartFile image,
            @RequestPart("metadata") UploadImageDetails imageDetails,
            @ApiIgnore @AuthenticationPrincipal @Nullable CustomUserDetails user
    );

    @GetMapping("/recent")
    ResponseEntity<List<ImagePreviewDetails>> getMostRecentImages();

    @DeleteMapping("/i/{token}")
    @PreAuthorize("hasAnyRole('ROLE_MOD','ROLE_ADMIN')")
    ResponseEntity<Void> removeImage(@PathVariable String token);
}
