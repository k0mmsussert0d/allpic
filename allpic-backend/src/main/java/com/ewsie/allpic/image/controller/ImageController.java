package com.ewsie.allpic.image.controller;

import com.ewsie.allpic.image.model.ImageDTO;
import com.ewsie.allpic.image.model.ImagePreviewDetails;
import com.ewsie.allpic.image.model.UploadImageDetails;
import com.ewsie.allpic.user.model.CustomUserDetails;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags={"Images"})
@RequestMapping("/img")
public interface ImageController {

    @ApiOperation(value = "Gets a full image")
    @GetMapping("/i/{token}")
    ResponseEntity<Resource> getImage(@PathVariable String token);

    @ApiOperation(value = "Gets thumbnail of an image")
    @GetMapping("/i/thumb/{token}")
    ResponseEntity<Resource> getImageThumbnail(@PathVariable String token);

    @ApiOperation(value = "Uploads an image")
    @PostMapping("/upload")
    ResponseEntity<ImageDTO> uploadImage(
            @RequestPart("file") MultipartFile image,
            @RequestPart("metadata") UploadImageDetails imageDetails,
            @AuthenticationPrincipal @Nullable CustomUserDetails user
    );

    @ApiOperation(value = "Gets the list of most recently uploaded public images")
    @GetMapping("/recent")
    ResponseEntity<List<ImagePreviewDetails>> getMostRecentImages();

    @ApiOperation(value = "Deletes the image")
    @DeleteMapping("/{token}")
    @PreAuthorize("hasAnyRole('ROLE_MOD','ROLE_ADMIN')")
    ResponseEntity<Void> removeImage(@PathVariable String token);
}
