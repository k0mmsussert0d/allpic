package com.ewsie.allpic.image.comment.service;

import com.ewsie.allpic.image.comment.model.CommentDTO;

public interface CommentDTOService {
    CommentDTO findById(Long id);

    CommentDTO save(CommentDTO commentDTO);
}
