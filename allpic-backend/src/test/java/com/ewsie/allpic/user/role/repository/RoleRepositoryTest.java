package com.ewsie.allpic.user.role.repository;

import com.ewsie.allpic.user.role.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void whenFindByRoleName_thenReturnRole() {
        // given
        Role role = new Role();
        role.setRoleName("TEST_ROLE");
        entityManager.persist(role);
        entityManager.flush();

        // when
        Optional<Role> found = roleRepository.findByRoleName("TEST_ROLE");

        // then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getRoleName()).isEqualTo(role.getRoleName());
    }
}
