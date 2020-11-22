package com.ewsie.allpic.config;

import com.ewsie.allpic.user.role.Role;
import com.ewsie.allpic.user.role.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class DataLoaderTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void whenContextUp_allUserRolesInitialized() {
        // when
        //   app context initialized

        // given
        Optional<Role> userRole = roleRepository.findByRoleName("USER");
        Optional<Role> modRole = roleRepository.findByRoleName("MOD");
        Optional<Role> adminRole = roleRepository.findByRoleName("ADMIN");

        // then
        assertThat(userRole.isPresent());
        assertThat(modRole.isPresent());
        assertThat(adminRole.isPresent());
    }
}
