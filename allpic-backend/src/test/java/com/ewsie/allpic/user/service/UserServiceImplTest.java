package com.ewsie.allpic.user.service;

import com.ewsie.allpic.user.model.User;
import com.ewsie.allpic.user.repository.UserRepository;
import com.ewsie.allpic.user.role.Role;
import com.ewsie.allpic.user.role.repository.RoleRepository;
import com.ewsie.allpic.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceImplTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void whenCreateUser_thenReturnUser() {
        // given
        User userToBeCreated = getSampleUser("user1");

        // when
        User createdUser = userService.create(userToBeCreated);

        // then
        assertThat(createdUser.getId())
                .as("User has been granted ID")
                .isNotNull();
        assertThat(createdUser)
                .as("User has unchanged other parameters")
                .isEqualToIgnoringNullFields(userToBeCreated);
    }

    @Test
    public void whenGetAll_thenReturnUsers() {
        // given
        User user1 = getSampleUser("user1");
        User user2 = getSampleUser("user2");
        User user3 = getSampleUser("user3");
        List<User> toBeSaved = List.of(user1, user2, user3);
        userRepository.saveAll(toBeSaved);

        // when
        List<User> foundUsers = userService.getAll();

        // then
        assertThat(foundUsers)
                .as("All users have been saved")
                .hasSize(3)
                .usingElementComparatorIgnoringFields("id")
                .containsAll(toBeSaved);
    }

    @Test
    public void whenFindById_thenReturnUser() {
        // given
        User user = getSampleUser("user");
        User savedUser = userRepository.save(user);

        // when
        User foundUser = userService.findById(savedUser.getId());

        // then
        assertThat(foundUser)
                .as("Should have same properties as saved user")
                .isEqualToIgnoringNullFields(savedUser);
    }

    @Test
    public void whenFindByUsername_thenReturnUser() {
        // given
        User user = getSampleUser("user");
        User savedUser = userRepository.save(user);

        // when
        User foundUser = userService.findByUsername(savedUser.getUsername());

        // then
        assertThat(foundUser)
                .as("Should have same properties as saved user")
                .isEqualToIgnoringNullFields(savedUser);
    }

    @Test
    public void whenFindByEmail_thenReturnUser() {
        // given
        User user = getSampleUser("user");
        User savedUser = userRepository.save(user);

        // when
        User foundUser = userService.findByEmail(savedUser.getEmail());

        // then
        assertThat(foundUser)
                .as("Should have same properties as saved user")
                .isEqualToIgnoringNullFields(savedUser);
    }

    @Test
    public void whenFindUsersByRole_thenReturnUsers() {
        // given
        User user1 = getSampleUser("user1");
        User user2 = getSampleUser("user2");
        User user3 = getSampleUser("user3");
        Role role = new Role();
        role.setRoleName("TEST_ROLE");
        user1.setRole(role);
        user2.setRole(role);
        user3.setRole(role);
        List<User> toBeSaved = List.of(user1, user2, user3);
        Role savedRole = roleRepository.save(role);
        userRepository.saveAll(toBeSaved);

        // when
        List<User> foundUsers = userService.findUsersByRole(savedRole);

        // then
        assertThat(foundUsers)
                .as("All users with specified role should be returned")
                .hasSize(3)
                .usingElementComparatorIgnoringFields("id")
                .containsAll(toBeSaved);
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
