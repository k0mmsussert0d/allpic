package com.ewsie.allpic.user.role.service;

import com.ewsie.allpic.user.role.Role;
import com.ewsie.allpic.user.role.RoleDTO;
import com.ewsie.allpic.user.role.repository.RoleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RoleDTOServiceImplTest {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    RoleDTOService roleDTOService;

    @BeforeEach
    public void setUp() {
        roleDTOService = new RoleDTOServiceImpl(roleService, modelMapper);
    }

    @Test
    public void whenFindById_thenReturnRole() {
        // given
        Role role = getSampleRole("ROLE");
        Role saved = roleRepository.save(role);

        // when
        RoleDTO found = roleDTOService.findById(saved.getId());

        // then
        assertThat(found)
                .as("Should have same properties as saved role")
                .isEqualToComparingFieldByField(saved);
    }

    @Test
    public void whenFindByRoleName_thenReturnRole() {
        // given
        Role role = getSampleRole("ROLE");
        Role saved = roleRepository.save(role);

        // when
        RoleDTO found = roleDTOService.findByRoleName(role.getRoleName());

        // then
        assertThat(found)
                .as("Should have same properties as saved role")
                .isEqualToComparingFieldByField(saved);
    }

    private Role getSampleRole(String roleName) {
        Role role = new Role();
        role.setRoleName(roleName);

        return role;
    }
}
