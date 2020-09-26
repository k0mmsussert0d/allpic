package com.ewsie.allpic.user.security.service.impl;

import com.ewsie.allpic.config.AppConfig;
import com.ewsie.allpic.user.model.AuthenticatedUserDTO;
import com.ewsie.allpic.user.model.UserDTO;
import com.ewsie.allpic.user.role.RoleDTO;
import com.ewsie.allpic.user.security.service.UserLoginService;
import com.ewsie.allpic.user.service.UserDTOService;
import com.ewsie.allpic.user.session.SessionTokenService;
import com.ewsie.allpic.user.session.service.SessionDTOService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserLoginServiceImplTest {
    
    UserLoginService userLoginService;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    AppConfig appConfig;
    
    @MockBean
    UserDTOService userDTOService;
    
    @MockBean
    SessionTokenService sessionTokenService;
    
    @MockBean
    SessionDTOService sessionDTOService;
    
    @BeforeEach
    public void setUp() {
        userLoginService = new UserLoginServiceImpl(
                userDTOService,
                passwordEncoder,
                sessionTokenService,
                sessionDTOService,
                appConfig
        );
    }
    
    @Test
    public void whenLogin_andUserIsValid_thenReturnAuthenticatedUserDetailsAndSetCookieWithSessionIdentifier() {
        
        // given
        String username = "username";
        String password = "password";
        String sampleAuthToken = "SAMPLE_AUTH_TOKEN";

        RoleDTO roleDTO = RoleDTO.builder()
                .roleName("USER")
                .build();

        UserDTO userDTO = UserDTO.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(username + "@example.com")
                .isActive(true)
                .registerTime(LocalDateTime.now().minusHours(1))
                .role(roleDTO)
                .build();

        Mockito.when(userDTOService.findByUsername(username)).thenReturn(userDTO);
        Mockito.when(sessionTokenService.createToken()).thenReturn(sampleAuthToken);

        ResponseCookie cookie = ResponseCookie
                .from("authentication", sampleAuthToken)
                .maxAge(Duration.ofSeconds(appConfig.getCookieMaxAge()))
                .sameSite("Strict")
                .path("/")
                .httpOnly(true)
                .secure(appConfig.isCookieSecure())
                .build();
        
        // when
        ResponseEntity<AuthenticatedUserDTO> responseEntity = userLoginService.login(username, password);
        
        // then
        assertThat(responseEntity.getStatusCode())
                .isEqualByComparingTo(HttpStatus.OK);
        assertThat(responseEntity.getHeaders().entrySet())
                .as("Should set a cookie with authentication session identifier")
                .contains(new AbstractMap.SimpleEntry<>(HttpHeaders.SET_COOKIE, List.of(cookie.toString())));
        assertThat(responseEntity.getBody())
                .as("Should return basic info of authenticated user")
                .isEqualTo(AuthenticatedUserDTO.builder().username(username).role(roleDTO.getRoleName()).build());
    }
    
    @Test
    public void whenLogin_andUserIsValidButPasswordIsNotValid_thenReturnUnauthorizedError() {
        // given
        String username = "username";
        String password = "password";

        RoleDTO roleDTO = RoleDTO.builder()
                .roleName("USER")
                .build();

        UserDTO userDTO = UserDTO.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(username + "@example.com")
                .isActive(true)
                .registerTime(LocalDateTime.now().minusHours(1))
                .role(roleDTO)
                .build();
        
        Mockito.when(userDTOService.findByUsername(username)).thenReturn(userDTO);
        
        // when
        // then
        assertThatThrownBy(() -> userLoginService.login(username, "otherPassword"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(HttpStatus.UNAUTHORIZED.toString());
    }
    
    @Test
    public void whenLogin_andUserIsValidButNotActive_thenReturnUnauthorizedError() {
        // given
        String username = "username";
        String password = "password";

        RoleDTO roleDTO = RoleDTO.builder()
                .roleName("USER")
                .build();

        UserDTO userDTO = UserDTO.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(username + "@example.com")
                .isActive(false)
                .registerTime(LocalDateTime.now().minusHours(1))
                .role(roleDTO)
                .build();

        Mockito.when(userDTOService.findByUsername(username)).thenReturn(userDTO);
        
        // when
        // then
        assertThatThrownBy(() -> userLoginService.login(username, "otherPassword"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(HttpStatus.UNAUTHORIZED.toString());
    }
    
    @Test
    public void whenLogin_andUserIsNotValid_thenReturnUnauthorizedError() {
        // given
        String username = "username";
        String password = "password";

        Mockito.when(userDTOService.findByUsername(username)).thenReturn(null);
        
        // when
        // then
        assertThatThrownBy(() -> userLoginService.login(username, password))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(HttpStatus.UNAUTHORIZED.toString());
    }
}