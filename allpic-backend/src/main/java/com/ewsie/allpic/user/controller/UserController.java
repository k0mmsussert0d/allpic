package com.ewsie.allpic.user.controller;

import com.ewsie.allpic.image.model.ImageDTO;
import com.ewsie.allpic.user.model.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/user")
public interface UserController {

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<List<UserDTO>> users();

    @GetMapping("/{username}")
    ResponseEntity<UserDTO> userInfo(@PathVariable String username);

    @GetMapping("/{username}/images")
    ResponseEntity<List<ImageDTO>> userImages(@PathVariable String username);
}
