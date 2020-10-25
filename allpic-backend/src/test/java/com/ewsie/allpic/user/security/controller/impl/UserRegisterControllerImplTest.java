package com.ewsie.allpic.user.security.controller.impl;

import com.ewsie.allpic.user.model.UserDTO;
import com.ewsie.allpic.user.role.RoleDTO;
import com.ewsie.allpic.user.security.service.UserRegisterService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserRegisterControllerImplTest {

    @MockBean
    UserRegisterService userRegisterService;

    @Autowired
    MockMvc mockMvc;

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "username@example.com";

    @Test
    public void whenRegister_andUsernameIsAlreadyTaken_returnConflictError() throws Exception {

        Mockito.when(userRegisterService.register(USERNAME, PASSWORD, EMAIL))
                .thenThrow(new ResponseStatusException(HttpStatus.CONFLICT));

        mockMvc.perform(
                post("/register/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"username\": \"username\",\n" +
                                "  \"password\": \"password\",\n" +
                                "  \"email\": \"username@example.com\"\n" +
                                "}")

        )
                .andExpect(status().isConflict());
    }

    @Test
    public void whenRegister_andEmailIsAlreadyUsed_returnConflictError() throws Exception {

        Mockito.when(userRegisterService.register(USERNAME, PASSWORD, EMAIL))
                .thenThrow(new ResponseStatusException(HttpStatus.CONFLICT));

        mockMvc.perform(
                post("/register/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"username\": \"username\",\n" +
                                "  \"password\": \"password\",\n" +
                                "  \"email\": \"username@example.com\"\n" +
                                "}")

        )
                .andExpect(status().isConflict());
    }

    @Test
    public void whenRegister_andPasswordRequirementsAreNotMet_returnBadRequestError() throws Exception {

        Mockito.when(userRegisterService.register(USERNAME, PASSWORD, EMAIL))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

        mockMvc.perform(
                post("/register/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"username\": \"username\",\n" +
                                "  \"password\": \"password\",\n" +
                                "  \"email\": \"username@example.com\"\n" +
                                "}")
        )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenRegister_andAllRequirementsAreMet_returnRegisteredUserDetails() throws Exception {

        RoleDTO defaultRole = RoleDTO.builder()
                .roleName("USER")
                .build();

        UserDTO registeredUser = UserDTO.builder()
                .id(1L)
                .username(USERNAME)
                .password("encoded_password")
                .email(EMAIL)
                .registerTime(LocalDateTime.of(2020, 1, 1, 12, 0, 0))
                .isActive(true)
                .role(defaultRole)
                .build();

        Mockito.when(userRegisterService.register(USERNAME, PASSWORD, EMAIL))
                .thenReturn(ResponseEntity.ok(registeredUser));

        mockMvc.perform(
                post("/register/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"username\": \"username\",\n" +
                                "  \"password\": \"password\",\n" +
                                "  \"email\": \"username@example.com\"\n" +
                                "}")

        )
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{\n" +
                                "  " +
                                "\"id\": null,\n" +
                                "  \"username\": \"username\",\n" +
                                "  \"email\": \"username@example.com\",\n" +
                                "  \"registerTime\": \"2020-01-01T12:00:00\",\n" +
                                "  \"isActive\": true,\n" +
                                "  \"role\": \"USER\"\n" +
                                "}"));
    }
}
