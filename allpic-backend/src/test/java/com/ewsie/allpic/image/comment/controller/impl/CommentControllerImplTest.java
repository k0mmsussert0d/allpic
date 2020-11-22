package com.ewsie.allpic.image.comment.controller.impl;

import com.ewsie.allpic.image.comment.model.CommentDTO;
import com.ewsie.allpic.image.comment.service.UnpublishCommentService;
import com.ewsie.allpic.image.model.ImageDTO;
import com.ewsie.allpic.image.service.ImageDTOService;
import com.ewsie.allpic.user.model.UserDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerImplTest {

    @MockBean
    ImageDTOService imageDTOService;

    @MockBean
    UnpublishCommentService unpublishCommentService;

    @Autowired
    MockMvc mockMvc;

    private static final String TOKEN = "ABCDEF";

    @Test
    public void whenGetCommentsForImage_andImageDoesNotExist_thenReturnNotFoundError() throws Exception {

        Mockito.when(imageDTOService.findByToken(TOKEN))
                .thenReturn(null);

        mockMvc.perform(get("/comment/" + TOKEN))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenGetCommentsForImage_andImageHasNoComments_thenReturnEmptyList() throws Exception {

        ImageDTO imageDTO = getSampleImage(1L, TOKEN);
        imageDTO.setComments(Collections.emptyList());

        Mockito.when(imageDTOService.findByToken(TOKEN))
                .thenReturn(imageDTO);

        mockMvc.perform(get("/comment/" + TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void whenGetCommentsForImage_andImageHasComments_thenReturnListOfPublicComments() throws Exception {

        UserDTO userDTO = getSampleUserDTO("username");

        ImageDTO imageDTO = getSampleImage(1L, TOKEN);
        imageDTO.setUploader(userDTO);

        CommentDTO commentDTO1 = getSampleComment(2L, "comment 1", userDTO);
        CommentDTO commentDTO2 = getSampleComment(3L, "comment 2", userDTO);
        CommentDTO commentDTO3 = getSampleComment(4L, "comment 3", userDTO);
        CommentDTO commentDTO4 = getSampleComment(5L, "comment 4", userDTO);
        commentDTO4.setIsPublic(false);

        imageDTO.setComments(List.of(commentDTO1, commentDTO2, commentDTO3));

        Mockito.when(imageDTOService.findByToken(TOKEN))
                .thenReturn(imageDTO);

        mockMvc.perform(get("/comment/" + TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "  {\n" +
                        "    \"id\": 2,\n" +
                        "    \"message\": \"comment 1\",\n" +
                        "    \"timeAdded\": \"2020-01-01T12:00:00\",\n" +
                        "    \"isPublic\": true,\n" +
                        "    \"author\": \"username\"\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"id\": 3,\n" +
                        "    \"message\": \"comment 2\",\n" +
                        "    \"timeAdded\": \"2020-01-01T12:00:00\",\n" +
                        "    \"isPublic\": true,\n" +
                        "    \"author\": \"username\"\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"id\": 4,\n" +
                        "    \"message\": \"comment 3\",\n" +
                        "    \"timeAdded\": \"2020-01-01T12:00:00\",\n" +
                        "    \"isPublic\": true,\n" +
                        "    \"author\": \"username\"\n" +
                        "  }\n" +
                        "]"));
    }

    @Test
    public void whenRemoveComment_andUserIsAnonymous_thenReturnUnauthorizedError() throws Exception {

        mockMvc.perform(delete("/comment/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = {"MOD"})
    public void whenRemoveComment_andUserHasRoleMod_andCommentDoesNotExist_thenReturnNotFoundError() throws Exception {

        Mockito.doThrow(NullPointerException.class)
                .when(unpublishCommentService)
                .unpublishCommentById(1L);

        mockMvc.perform(delete("/comment/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = {"MOD"})
    public void whenRemoveComment_andUserHasRoleMod_andCommentExists_thenReturnOK() throws Exception {

        mockMvc.perform(delete("/comment/1"))
                .andExpect(status().isOk());
    }

    private ImageDTO getSampleImage(Long id, String token) {
        return ImageDTO.builder()
                .id(id)
                .token(token)
                .isActive(true)
                .isPublic(true)
                .uploadTime(LocalDateTime.of(2020, 1, 1, 12, 0, 0, 0))
                .build();
    }

    private UserDTO getSampleUserDTO(String username) {
        return UserDTO.builder()
                .username(username)
                .password("password")
                .email(username + "@example.com")
                .isActive(true)
                .registerTime(LocalDateTime.now())
                .build();
    }

    private CommentDTO getSampleComment(Long id, String message, UserDTO author) {
        return CommentDTO.builder()
                .id(id)
                .author(author)
                .message(message)
                .isPublic(true)
                .timeAdded(LocalDateTime.of(2020, 1, 1, 12, 0, 0))
                .build();
    }
}
