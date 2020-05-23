package com.ewsie.allpic.image.comment.service.impl;

import com.ewsie.allpic.image.comment.model.Comment;
import com.ewsie.allpic.image.comment.model.CommentDTO;
import com.ewsie.allpic.image.comment.service.CommentDTOService;
import com.ewsie.allpic.image.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentDTOServiceImpl implements CommentDTOService {

    private final ModelMapper modelMapper;
    private final CommentService commentService;

    @Override
    public CommentDTO findById(Long id) {
        Optional<Comment> comment = Optional.ofNullable(commentService.findById(id));

        if (comment.isEmpty()) {
            return null;
        }

        return modelMapper.map(comment.get(), CommentDTO.class);
    }

    @Override
    public CommentDTO save(CommentDTO commentDTO) {
        Comment savedComment = commentService.save(modelMapper.map(commentDTO, Comment.class));
        return modelMapper.map(savedComment, CommentDTO.class);
    }
}
