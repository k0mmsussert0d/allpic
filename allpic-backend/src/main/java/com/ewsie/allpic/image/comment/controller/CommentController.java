package com.ewsie.allpic.image.comment.controller;

import com.ewsie.allpic.image.comment.model.CommentDTO;
import com.ewsie.allpic.user.model.CustomUserDetails;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags={"Comments"})
@RequestMapping("/comment")
public interface CommentController {

    @ApiOperation(value = "Gets all comments of the image")
    @GetMapping("/{imageToken}")
    ResponseEntity<List<CommentDTO>> getCommentsForImage(@PathVariable String imageToken);

    @ApiOperation(value = "Posts a comment of the image")
    @PostMapping("/{imageToken}")
    @PreAuthorize("isFullyAuthenticated()")
    ResponseEntity<CommentDTO> addComment(
            @PathVariable String imageToken,
            @AuthenticationPrincipal CustomUserDetails author,
            @RequestBody String message
    );

    @ApiOperation(value = "Deletes the comment")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MOD','ROLE_ADMIN')")
    ResponseEntity<Void> removeComment(@PathVariable Long id);
}
