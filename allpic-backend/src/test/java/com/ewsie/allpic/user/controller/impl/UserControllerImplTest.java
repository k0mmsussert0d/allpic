package com.ewsie.allpic.user.controller.impl;

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
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDTOService userDTOService;

    @MockBean
    private ImageDTOService imageDTOService;

    //language=JSON
    private static final String DEFAULT_USERS_JSON =
            "[" +
            "{\"id\": null, \"username\": \"user1\", \"email\": \"user1@example.com\", \"registerTime\": \"2020-01-01T12:00:00\", \"isActive\": true, \"role\":  null}" +
            "," +
            "{\"id\": null, \"username\": \"user2\", \"email\": \"user2@example.com\", \"registerTime\": \"2020-01-01T12:00:00\", \"isActive\": true, \"role\":  null}" +
            "," +
            "{\"id\": null, \"username\": \"user3\", \"email\": \"user3@example.com\", \"registerTime\": \"2020-01-01T12:00:00\", \"isActive\": true, \"role\":  null}" +
            "]";

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
                .andExpect(content().json(DEFAULT_USERS_JSON));
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
}
