package com.ewsie.allpic.user.service.impl;

import com.ewsie.allpic.user.model.User;
import com.ewsie.allpic.user.model.UserDTO;
import com.ewsie.allpic.user.role.Role;
import com.ewsie.allpic.user.role.RoleDTO;
import com.ewsie.allpic.user.role.repository.RoleRepository;
import com.ewsie.allpic.user.service.UserDTOService;
import com.ewsie.allpic.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserDTOServiceImplTest {

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ModelMapper modelMapper;

    UserDTOService userDTOService;

    @BeforeEach
    public void setUp() {
        userDTOService = new UserDTOServiceImpl(userService, modelMapper);
    }

    @Test
    public void whenCreateUser_thenReturnUser() {
        // given
        UserDTO user = getSampleUserDTO("user");

        // when
        UserDTO createdUser = userDTOService.create(user);

        // then
        assertThat(createdUser.getId())
                .as("User has been granted ID")
                .isNotNull();
        assertThat(createdUser)
                .as("User has unchanged other parameters")
                .isEqualToIgnoringGivenFields(user, "id");
    }

    @Test
    public void whenGetAll_thenReturnUsers() {
        // given
        User user1 = getSampleUser("user1");
        User user2 = getSampleUser("user2");
        User user3 = getSampleUser("user3");
        List<User> toBeSaved = List.of(user1, user2, user3);
        toBeSaved.forEach(user -> userService.create(user));

        // when
        List<UserDTO> foundUsers = userDTOService.getAll();

        // then
        assertThat(foundUsers)
                .as("All saved users should be returned")
                .usingElementComparatorIgnoringFields("id", "role")
                .containsAll(toBeSaved.stream().map(u -> modelMapper.map(u, UserDTO.class)).collect(Collectors.toList()));
    }

    @Test
    public void whenFindById_thenReturnUser() {
        // given
        User user = getSampleUser("user");
        User savedUser = userService.create(user);

        // when
        UserDTO foundUser = userDTOService.findById(savedUser.getId());

        // then
        assertThat(foundUser)
                .as("Should have same properties as saved user")
                .isEqualTo(modelMapper.map(savedUser, UserDTO.class));
    }

    @Test
    public void whenFindByUsername_thenReturnUser() {
        // given
        User user = getSampleUser("user");
        User savedUser = userService.create(user);

        // when
        UserDTO foundUser = userDTOService.findByUsername(savedUser.getUsername());

        // then
        assertThat(foundUser)
                .as("Should have same properties as saved user")
                .isEqualTo(modelMapper.map(savedUser, UserDTO.class));
    }

    @Test
    public void whenFindByEmail_thenReturnUser() {
        // given
        User user = getSampleUser("user");
        User savedUser = userService.create(user);

        // when
        UserDTO foundUser = userDTOService.findByEmail(savedUser.getEmail());

        // then
        assertThat(foundUser)
                .as("Should have same properties as saved user")
                .isEqualTo(modelMapper.map(savedUser, UserDTO.class));
    }

    @Test
    public void whenFindUsersByRole_thenReturnUsers() {
        // given
        User user1 = getSampleUser("user1");
        User user2 = getSampleUser("user2");
        User user3 = getSampleUser("user3");
        List<User> toBeSaved = List.of(user1, user2, user3);

        Role role = new Role();
        role.setRoleName("TEST_ROLE");
        user1.setRole(role);
        user2.setRole(role);
        user3.setRole(role);

        Role savedRole = roleRepository.save(role);
        toBeSaved.forEach(u -> userService.create(u));

        // when
        List<UserDTO> foundUsers = userDTOService.findUsersByRole(modelMapper.map(savedRole, RoleDTO.class));

        // then
        assertThat(foundUsers)
                .as("All users with specified role should be returned")
                .extracting(userDTO -> modelMapper.map(userDTO, User.class))
                .usingElementComparatorIgnoringFields("id")
                .containsAll(toBeSaved);
    }

    private User getSampleUser(String username) {
        User user = new User();
        user.setUsername(username);
        user.setPassword("password");
        user.setEmail(username + "@example.com");
        user.setIsActive(true);
        user.setRegisterTime(LocalDateTime.now());

        return user;
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
}
