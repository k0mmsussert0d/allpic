package com.ewsie.allpic.image.comment.service;

import com.ewsie.allpic.image.comment.model.CommentDTO;
import com.ewsie.allpic.image.model.ImageDTO;

public interface PublishCommentService {

    CommentDTO publishComment(CommentDTO comment, ImageDTO image);
}
