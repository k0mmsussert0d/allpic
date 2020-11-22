package com.ewsie.allpic.image.comment.service.impl;

import com.ewsie.allpic.image.comment.model.Comment;
import com.ewsie.allpic.image.comment.repository.CommentRepository;
import com.ewsie.allpic.image.comment.service.CommentService;
import com.ewsie.allpic.image.model.Image;
import com.ewsie.allpic.user.model.User;
import com.ewsie.allpic.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceImplTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserService userService;

    CommentService commentService;

    @BeforeEach
    public void setUp() {
        commentService = new CommentServiceImpl(commentRepository);
    }

    @Test
    public void whenFindById_thenReturnComment() {
        // given
        Comment comment = getSampleComment();
        User user = getSampleUser("USER");
        comment.setAuthor(user);

        userService.create(user);
        Comment savedComment = commentRepository.save(comment);
        commentRepository.flush();

        // when
        Optional<Comment> found = Optional.ofNullable(commentService.findById(savedComment.getId()));

        // then
        assertThat(found).isPresent();
        assertThat(found.get()).isEqualTo(savedComment);
    }

    @Test
    public void whenSave_thenReturnSavedComment() {
        // given
        Comment comment = getSampleComment();
        User user = getSampleUser("USER");
        comment.setAuthor(user);

        userService.create(user);

        // when
        Comment savedComment = commentService.save(comment);

        // then
        assertThat(savedComment.getId())
                .as("Comment has been granted ID")
                .isNotNull();
        assertThat(savedComment)
                .as("Comment has unchanged other paramters")
                .isEqualTo(comment);
    }

    private Comment getSampleComment() {
        Comment comment = new Comment();
        comment.setMessage("sample message");
        comment.setTimeAdded(LocalDateTime.now());

        return comment;
    }

    private User getSampleUser(String username) {
        User user = new User();
        user.setUsername(username);

        //   filling mandatory NOT NULL fields
        user.setPassword("password");
        user.setEmail(username + "@example.com");
        user.setRegisterTime(LocalDateTime.now());
        user.setIsActive(true);
        return user;
    }
}
