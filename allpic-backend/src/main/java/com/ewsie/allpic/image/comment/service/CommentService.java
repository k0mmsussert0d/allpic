package com.ewsie.allpic.image.comment.service;

import com.ewsie.allpic.image.comment.model.Comment;

public interface CommentService {
    Comment findById(Long id);

    Comment save(Comment comment);
}
