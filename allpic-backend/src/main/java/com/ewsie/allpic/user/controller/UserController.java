package com.ewsie.allpic.user.controller;

import com.ewsie.allpic.image.model.ImageDTO;
import com.ewsie.allpic.user.model.CustomUserDetails;
import com.ewsie.allpic.user.model.UserDTO;
import com.ewsie.allpic.user.model.request.ResetPasswordRequestBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags={"User info"})
@RequestMapping("/user")
public interface UserController {

    @ApiOperation(value = "Returns list of users")
    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<List<UserDTO>> users();

    @ApiOperation(value = "Returns single user")
    @GetMapping("/{username}")
    ResponseEntity<UserDTO> userInfo(@PathVariable String username);

    @ApiOperation(value = "Gets user avatar")
    @GetMapping("/{username}/avatar")
    ResponseEntity<Resource> userAvatar(@PathVariable String username);

    @ApiOperation(value = "Returns list of images uploaded by user authenticated with session cookie")
    @GetMapping("/images")
    @PreAuthorize("isFullyAuthenticated()")
    ResponseEntity<List<ImageDTO>> userImages(@AuthenticationPrincipal CustomUserDetails userDetails);

    @ApiOperation(value = "Resets password of authenticated user")
    @PutMapping("/reset")
    @PreAuthorize("isFullyAuthenticated()")
    ResponseEntity<UserDTO> changePassword(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody ResetPasswordRequestBody body);
}
