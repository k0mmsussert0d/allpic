package com.ewsie.allpic.image.comment.service.impl;

import com.ewsie.allpic.image.comment.model.Comment;
import com.ewsie.allpic.image.comment.repository.CommentRepository;
import com.ewsie.allpic.image.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Comment findById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);

        if (comment.isEmpty()) {
            return null;
        }

        return comment.get();
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }
}
