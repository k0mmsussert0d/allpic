package com.ewsie.allpic.user.security.controller.impl;

import com.ewsie.allpic.config.AppConfig;
import com.ewsie.allpic.user.controller.impl.SpringSecurityForUserControllerImplTestConfig;
import com.ewsie.allpic.user.model.AuthenticatedUserDTO;
import com.ewsie.allpic.user.model.UserDTO;
import com.ewsie.allpic.user.model.request.UserLoginRequestBody;
import com.ewsie.allpic.user.security.service.UserLoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        classes = SpringSecurityForUserControllerImplTestConfig.class
)
@AutoConfigureMockMvc
class UserAuthControllerImplTest {

    @MockBean
    UserLoginService userLoginService;

    @MockBean
    AppConfig appConfig;

    @Autowired
    UserDTO testUser;

    @Autowired
    MockMvc mockMvc;

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String ROLE = "user";
    private static final String SESSION_ID = "sample_session_identifier";

    @BeforeEach
    public void setUpAppConfig() {

        Mockito.when(appConfig.getCookieMaxAge())
                .thenReturn(3600L);

        Mockito.when(appConfig.isCookieSecure())
                .thenReturn(true);

        Mockito.when(appConfig.getCorsHosts())
                .thenReturn("*");
    }

    @Test
    public void whenLogin_andCredentialsAreValid_thenReturnAuthenticatedUserDetailsAndSetCookie() throws Exception {

        AuthenticatedUserDTO body = new AuthenticatedUserDTO(USERNAME, ROLE);
        MultiValueMap<String, String> headers = new HttpHeaders();
        ResponseCookie cookie = ResponseCookie
                .from("authentication", SESSION_ID)
                .maxAge(3600L)
                .sameSite("Strict")
                .path("/")
                .httpOnly(true)
                .secure(true)
                .build();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());

        Mockito.when(userLoginService.login(USERNAME, PASSWORD))
                .thenReturn(new ResponseEntity<>(body, headers, HttpStatus.OK));

        UserLoginRequestBody requestBody = new UserLoginRequestBody();
        requestBody.setUsername(USERNAME);
        requestBody.setPassword(PASSWORD);

        ObjectMapper om = new ObjectMapper();
        String requestBodyJson = om.writeValueAsString(requestBody);


        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyJson))
                .andExpect(status().isOk())
                .andExpect(cookie().value("authentication", SESSION_ID))
                .andExpect(cookie().httpOnly("authentication", true))
                .andExpect(cookie().secure("authentication", true))
                .andExpect(cookie().path("authentication", "/"))
                .andExpect(content().json("{\n" +
                        "  \"username\": \"username\",\n" +
                        "  \"role\": \"user\"\n" +
                        "}"));
    }

    @Test
    public void whenLogin_andCredentialsAreNotValid_thenReturnUnauthorizedError() throws Exception {

        UserLoginRequestBody requestBody = new UserLoginRequestBody();
        requestBody.setUsername(USERNAME);
        requestBody.setPassword(PASSWORD);
        ObjectMapper om = new ObjectMapper();
        String requestBodyJson = om.writeValueAsString(requestBody);

        Mockito.when(userLoginService.login(USERNAME, PASSWORD))
                .thenThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyJson))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void whenAuthenticate_andAuthorizationHeaderDoesNotContainValidSessionID_thenReturnUnauthorizedError() throws Exception {

        mockMvc.perform(get("/auth/"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails("username")
    public void whenAuthenticate_andAuthorizationHeaderContainsValidSessionID_thenReturnAuthenticatedUserDetails() throws Exception {

        mockMvc.perform(get("/auth/"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"username\": \"username\",\n" +
                        "  \"role\": \"ROLE_USER\"\n" +
                        "}"));    }
}
