package com.ewsie.allpic.image.comment.service.impl;

import com.ewsie.allpic.image.comment.model.CommentDTO;
import com.ewsie.allpic.image.comment.service.CommentDTOService;
import com.ewsie.allpic.image.comment.service.PublishCommentService;
import com.ewsie.allpic.image.model.ImageDTO;
import com.ewsie.allpic.image.service.ImageDTOService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublishCommentServiceImpl implements PublishCommentService {

    private final CommentDTOService commentDTOService;
    private final ImageDTOService imageDTOService;

    @Override
    public CommentDTO publishComment(CommentDTO comment, ImageDTO image) {
        CommentDTO savedComment = commentDTOService.save(comment);
        image.addComment(savedComment);
        imageDTOService.save(image);

        return savedComment;
    }
}
