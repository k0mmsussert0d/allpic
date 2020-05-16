package com.ewsie.allpic.image.controller;

import com.ewsie.allpic.user.session.model.SessionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/img")
public interface ImageController {

    @GetMapping("/i/{id}")
    ResponseEntity<String> getImage(@PathVariable String id);

    @PostMapping("/upload")
    ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile image, @RequestParam("session_id") String sessionId);
}
