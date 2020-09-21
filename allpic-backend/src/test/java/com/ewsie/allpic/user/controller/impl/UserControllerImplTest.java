package com.ewsie.allpic.user.controller.impl;

import com.ewsie.allpic.image.model.ImageDTO;
import com.ewsie.allpic.image.service.ImageDTOService;
import com.ewsie.allpic.user.model.UserDTO;
import com.ewsie.allpic.user.service.UserDTOService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        classes = SpringSecurityForUserControllerImplTestConfig.class
)
@AutoConfigureMockMvc
class UserControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDTOService userDTOService;

    @MockBean
    private ImageDTOService imageDTOService;

    @Autowired
    UserDTO testUser;

    @Test
    public void whenGetUsers_asAnonymous_shouldReturnUnauthorized() throws Exception {

        mockMvc.perform(get("/user/"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void whenGetUsers_asAdmin_andUserListIsEmpty_shouldReturnNotFoundError() throws Exception {

        Mockito.when(userDTOService.getAll()).thenReturn(null);

        mockMvc.perform(get("/user/"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void whenGetUsers_asAdmin_andUserListIsNotEmpty_shouldReturnUsers() throws Exception {

        List<UserDTO> users = getSampleUsers();

        Mockito.when(userDTOService.getAll()).thenReturn(users);

        mockMvc.perform(get("/user/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                        "[" +
                                "{\"id\": null, \"username\": \"user1\", \"email\": \"user1@example.com\", \"registerTime\": \"2020-01-01T12:00:00\", \"isActive\": true, \"role\":  null}" +
                                "," +
                                "{\"id\": null, \"username\": \"user2\", \"email\": \"user2@example.com\", \"registerTime\": \"2020-01-01T12:00:00\", \"isActive\": true, \"role\":  null}" +
                                "," +
                                "{\"id\": null, \"username\": \"user3\", \"email\": \"user3@example.com\", \"registerTime\": \"2020-01-01T12:00:00\", \"isActive\": true, \"role\":  null}" +
                                "]"
                ));
    }

    @Test
    public void whenGetUser_andUserIsFound_thenReturnUserInfo() throws Exception {

        String username = "username";
        UserDTO user = getSampleUser(username);

        Mockito.when(userDTOService.findByUsername(username)).thenReturn(user);

        mockMvc.perform(get("/user/" + username))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                        "{\"id\": null, \"username\": \"username\", \"email\": \"username@example.com\", \"registerTime\": \"2020-01-01T12:00:00\", \"isActive\": true, \"role\":  null}"
                ));
    }

    @Test
    public void whenGetUser_andUserIsNotFound_thenReturnNotFoundError() throws Exception {

        String username = "username";
        Mockito.when(userDTOService.findByUsername(username)).thenReturn(null);

        mockMvc.perform(get("/user/" + username))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails("username")
    public void whenGetUserImages_andUserIsAuthorized_thenReturnListOfImages() throws Exception {

        List<ImageDTO> images = getSampleImages();
        images.forEach(imageDTO -> imageDTO.setUploader(testUser));

        Mockito.when(imageDTOService.findAllUploadedBy(testUser)).thenReturn(images);

        mockMvc.perform(get("/user/images/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(
                        "[\n" +
                                "  {\n" +
                                "    \"id\": 1,\n" +
                                "    \"isActive\": true,\n" +
                                "    \"isPublic\": true,\n" +
                                "    \"title\": null,\n" +
                                "    \"token\": \"token1\",\n" +
                                "    \"uploadTime\": \"2020-01-01T12:00:00\",\n" +
                                "    \"uploader\": \"username\"\n" +
                                "  },\n" +
                                "  {\n" +
                                "    \"id\": 2,\n" +
                                "    \"isActive\": true,\n" +
                                "    \"isPublic\": true,\n" +
                                "    \"title\": null,\n" +
                                "    \"token\": \"token2\",\n" +
                                "    \"uploadTime\": \"2020-01-01T12:00:00\",\n" +
                                "    \"uploader\": \"username\"\n" +
                                "  },\n" +
                                "  {\n" +
                                "    \"id\": 3," +
                                "    \n" +
                                "    \"isActive\":true,\n" +
                                "    \"isPublic\": true,\n" +
                                "    \"title\": null,\n" +
                                "    \"token\": \"token3\",\n" +
                                "    \"uploadTime\": \"2020-01-01T12:00:00\",\n" +
                                "    \"uploader\": \"username\"" +
                                "  }\n" +
                                "]"
                ));
    }

    @Test
    @WithUserDetails("username")
    public void whenGetUserImages_andUserIsAuthorized_andUserHasNoImages_thenReturnEmptyList() throws Exception {

        Mockito.when(imageDTOService.findAllUploadedBy(testUser)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/user/images/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
    }

    @Test
    public void whenGetUserImages_andUserIsNotAuthorized_thenReturnUnauthorizedError() throws Exception {

        mockMvc.perform(get("/user/images/"))
                .andExpect(status().isUnauthorized());
    }

    private List<UserDTO> getSampleUsers() {
        UserDTO user1 = getSampleUser("user1");
        UserDTO user2 = getSampleUser("user2");
        UserDTO user3 = getSampleUser("user3");

        return List.of(user1, user2, user3);
    }

    private UserDTO getSampleUser(String username) {
        return UserDTO.builder()
                .username(username)
                .email(username + "@example.com")
                .password("password")
                .registerTime(LocalDateTime.of(2020, 1, 1, 12, 0, 0, 0))
                .isActive(true)
                .build();
    }

    private List<ImageDTO> getSampleImages() {
        ImageDTO image1 = getSampleImage(1L, "token1");
        ImageDTO image2 = getSampleImage(2L, "token2");
        ImageDTO image3 = getSampleImage(3L, "token3");

        return List.of(image1, image2, image3);
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
}
