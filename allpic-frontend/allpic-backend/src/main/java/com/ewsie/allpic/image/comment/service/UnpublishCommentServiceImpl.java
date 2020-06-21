package com.ewsie.allpic.image.comment.service;

import com.ewsie.allpic.image.comment.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UnpublishCommentServiceImpl implements UnpublishCommentService {

    private final CommentService commentService;

    @Override
    public void unpublishCommentById(Long id) throws NullPointerException {
        Optional<Comment> comment = Optional.ofNullable(commentService.findById(id));
        if (comment.isEmpty()) {
            throw new NullPointerException("Comment with id " + id + " not found");
        }

        comment.get().setIsPublic(false);
        commentService.save(comment.get());
    }
}
