package com.ewsie.allpic.image.comment.service.impl;

import com.ewsie.allpic.image.comment.model.Comment;
import com.ewsie.allpic.image.comment.model.CommentDTO;
import com.ewsie.allpic.image.comment.service.CommentDTOService;
import com.ewsie.allpic.image.comment.service.CommentService;
import com.ewsie.allpic.user.model.User;
import com.ewsie.allpic.user.model.UserDTO;
import com.ewsie.allpic.user.service.UserDTOService;
import com.ewsie.allpic.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentDTOServiceImplTest {

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Autowired
    UserDTOService userDTOService;

    @Autowired
    ModelMapper modelMapper;

    CommentDTOService commentDTOService;

    @BeforeEach
    public void setUp() {
        commentDTOService = new CommentDTOServiceImpl(modelMapper, commentService);
    }

    @Test
    public void whenFindById_thenReturnComment() {
        // given
        Comment comment = getSampleComment();
        User user = getSampleUser("username");
        comment.setAuthor(user);

        userService.create(user);
        Comment saved = commentService.save(comment);

        // when
        CommentDTO found = commentDTOService.findById(saved.getId());

        // then
        assertThat(found)
                .as("Should have same properties as saved comment")
                .isEqualToComparingFieldByField(saved);
    }

    @Test
    public void whenSave_thenReturnComment() {
        // given
        UserDTO userDTO = UserDTO.builder()
                .username("username")
                .email("username@example.com")
                .registerTime(LocalDateTime.now())
                .isActive(true)
                .build();
        UserDTO savedUser = userDTOService.create(userDTO);
        CommentDTO commentDTO = CommentDTO.builder()
                .isPublic(true)
                .message("message")
                .timeAdded(LocalDateTime.now())
                .author(savedUser)
                .build();


        // when
        CommentDTO saved = commentDTOService.save(commentDTO);

        // then
        assertThat(saved)
                .as("Should have same properties")
                .isEqualToIgnoringGivenFields(commentDTO, "id");
        assertThat(saved.getId())
                .as("Should have ID assigned")
                .isNotNull();
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
