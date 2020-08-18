package com.ewsie.allpic.user.controller;

import com.ewsie.allpic.image.model.ImageDTO;
import com.ewsie.allpic.user.model.CustomUserDetails;
import com.ewsie.allpic.user.model.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @ApiOperation(value = "Returns list of images uploaded by user authenticated with session cookie")
    @GetMapping("/images")
    @PreAuthorize("isFullyAuthenticated()")
    ResponseEntity<List<ImageDTO>> userImages(@AuthenticationPrincipal CustomUserDetails userDetails
    );
}
