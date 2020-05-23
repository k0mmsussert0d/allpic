package com.ewsie.allpic.image.comment.controller.impl;

import com.ewsie.allpic.image.comment.controller.CommentController;
import com.ewsie.allpic.image.comment.model.CommentDTO;
import com.ewsie.allpic.image.model.ImageDTO;
import com.ewsie.allpic.image.service.ImageDTOService;
import com.ewsie.allpic.user.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CommentControllerImpl implements CommentController {

    private final ImageDTOService imageDTOService;

    @Override
    public ResponseEntity<String> addComment(String imageToken, @AuthenticationPrincipal CustomUserDetails author, @RequestBody String message) {
        if (StringUtils.isEmpty(imageToken) || StringUtils.isEmpty(message)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No token or message provided");
        }

        Optional<ImageDTO> imageToAddCommentTo = Optional.ofNullable(imageDTOService.findByToken(imageToken));
        if (imageToAddCommentTo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image with token " + imageToken + "not found");
        }

        CommentDTO newComment = CommentDTO.builder()
                .message(message)
                .author(author.getUser())
                .isPublic(true)
                .timeAdded(LocalDateTime.now())
                .build();

        imageToAddCommentTo.get().addComment(newComment);
        imageDTOService.save(imageToAddCommentTo.get());

        return ResponseEntity.status(HttpStatus.OK).body("Comment added to " + imageToken + " as " + author.getUsername());
    }

    @Override
    public ResponseEntity<List<CommentDTO>> getCommentsForImage(String imageToken) {
        Optional<ImageDTO> requestedImage = Optional.ofNullable(imageDTOService.findByToken(imageToken));
        if (requestedImage.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(requestedImage.get().getComments());
    }
}
