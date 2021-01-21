package com.ewsie.allpic.image.controller;

import com.ewsie.allpic.user.model.CustomUserDetails;
import com.ewsie.allpic.user.model.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Api(tags={"User info"})
@RequestMapping("/user/av")
public interface AvatarController {

    @ApiOperation("Sets or updates user avatar")
    @PostMapping("/")
    @PreAuthorize("isFullyAuthenticated()")
    ResponseEntity<UserDTO> updateAvatar(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam("file") MultipartFile file);

    @DeleteMapping("/")
    @PreAuthorize("isFullyAuthenticated()")
    ResponseEntity<UserDTO> deleteAvatar();
}
