package com.ewsie.allpic.image.comment.repository;

import com.ewsie.allpic.image.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findById(Long id);
}
