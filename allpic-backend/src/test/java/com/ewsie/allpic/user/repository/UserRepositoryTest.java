package com.ewsie.allpic.user.repository;

import com.ewsie.allpic.user.model.User;
import com.ewsie.allpic.user.role.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByName_thenReturnUser() {
        // given
        User foo = getSampleUser();
        entityManager.persist(foo);
        entityManager.flush();

        // when
        Optional<User> found = userRepository.findByUsername("foo");

        // then
        assertThat(found.isPresent());
        assertThat(found.get().getUsername())
                .isEqualTo(foo.getUsername());
    }

    @Test
    public void whenFindByEmail_thenReturnUser() {
        // given
        User foo = getSampleUser();
        entityManager.persist(foo);
        entityManager.flush();

        // when
        Optional<User> found = userRepository.findByEmail("email@example.com");

        // then
        assertThat(found.isPresent());
        assertThat(found.get().getEmail())
                .isEqualTo(foo.getEmail());
    }

    @Test
    public void whenFindByRole_thenReturnUsers() {
        // given
        User user1 = getSampleUser();
        User user2 = new User();
        user2.setUsername("foo1");
        user2.setPassword("password");
        user2.setEmail("email1@example.com");
        user2.setRegisterTime(LocalDateTime.now());
        user2.setIsActive(true);

        Role role = new Role();
        role.setRoleName("TEST_ROLE");

        user1.setRole(role);
        user2.setRole(role);

        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.persist(role);
        entityManager.flush();

        // when
        Optional<List<User>> found = userRepository.findUsersByRole(role);

        // then
        assertThat(found.isPresent());
        assertThat(found.get().contains(user1));
        assertThat(found.get().contains(user2));
    }

    private User getSampleUser() {
        User foo = new User();
        foo.setUsername("foo");

        //   filling mandatory NOT NULL fields
        foo.setPassword("password");
        foo.setEmail("email@example.com");
        foo.setRegisterTime(LocalDateTime.now());
        foo.setIsActive(true);
        return foo;
    }

}
