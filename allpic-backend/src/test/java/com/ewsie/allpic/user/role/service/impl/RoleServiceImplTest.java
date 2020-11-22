package com.ewsie.allpic.user.role.service.impl;

import com.ewsie.allpic.user.role.Role;
import com.ewsie.allpic.user.role.repository.RoleRepository;
import com.ewsie.allpic.user.role.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class RoleServiceImplTest {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleService roleService;

    @BeforeEach
    public void setUp() {
        roleService = new RoleServiceImpl(roleRepository);
    }

    @Test
    public void whenFindById_thenReturnRole() {
        // given
        Role role = getSampleRole("TEST_ROLE");
        Role saved = roleRepository.save(role);

        // when
        Role found = roleService.findById(saved.getId());

        // then
        assertThat(found).isEqualTo(saved);
    }

    @Test
    public void whenFindByRoleName_thenReturnRole() {
        // given
        Role role = getSampleRole("TEST_ROLE");
        roleRepository.save(role);

        // when
        Role found = roleService.findByRoleName(role.getRoleName());

        // then
        assertThat(found).isEqualToComparingOnlyGivenFields(role, "roleName");
    }

    private Role getSampleRole(String roleName) {
        Role role = new Role();
        role.setRoleName(roleName);

        return role;
    }
}
