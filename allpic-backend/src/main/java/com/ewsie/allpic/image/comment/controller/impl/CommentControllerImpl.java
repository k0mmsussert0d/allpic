package com.ewsie.allpic.image.comment.controller.impl;

import com.ewsie.allpic.image.comment.controller.CommentController;
import com.ewsie.allpic.image.comment.model.CommentDTO;
import com.ewsie.allpic.image.comment.service.PublishCommentService;
import com.ewsie.allpic.image.comment.service.UnpublishCommentService;
import com.ewsie.allpic.image.model.ImageDTO;
import com.ewsie.allpic.image.service.ImageDTOService;
import com.ewsie.allpic.user.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CommentControllerImpl implements CommentController {

    private final ImageDTOService imageDTOService;
    private final PublishCommentService publishCommentService;
    private final UnpublishCommentService unpublishCommentService;

    @Override
    public ResponseEntity<List<CommentDTO>> getCommentsForImage(String imageToken) {
        Optional<ImageDTO> requestedImage = Optional.ofNullable(imageDTOService.findByToken(imageToken));
        if (requestedImage.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(requestedImage.get().getComments());
    }

    @Override
    public ResponseEntity<CommentDTO> addComment(
            String imageToken,
            @AuthenticationPrincipal CustomUserDetails author,
            @RequestBody String message
    ) {
        Optional<ImageDTO> imageToAddCommentTo = Optional.ofNullable(imageDTOService.findByToken(imageToken));
        if (imageToAddCommentTo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image with token " + imageToken + " not found");
        }

        CommentDTO newComment = CommentDTO.builder()
                .message(message)
                .author(author.getUser())
                .isPublic(true)
                .timeAdded(LocalDateTime.now())
                .build();

        CommentDTO publishedComment = publishCommentService.publishComment(newComment, imageToAddCommentTo.get());

        return ResponseEntity.status(HttpStatus.OK).body(publishedComment);
    }

    @Override
    public ResponseEntity<Void> removeComment(Long id) {
        try {
            unpublishCommentService.unpublishCommentById(id);
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment with an ID " + id + " does not exist");
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
