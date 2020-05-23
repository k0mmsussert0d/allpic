package com.ewsie.allpic.image.comment.controller;

import com.ewsie.allpic.image.comment.model.CommentDTO;
import com.ewsie.allpic.user.model.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/comment")
public interface CommentController {

    @PutMapping("/{imageToken}")
    @PreAuthorize("isFullyAuthenticated()")
    ResponseEntity<String> addComment(
            @PathVariable String imageToken,
            @AuthenticationPrincipal CustomUserDetails author,
            @RequestBody String message
    );

    @GetMapping("{imageToken}")
    ResponseEntity<List<CommentDTO>> getCommentsForImage(@PathVariable String imageToken);
}
