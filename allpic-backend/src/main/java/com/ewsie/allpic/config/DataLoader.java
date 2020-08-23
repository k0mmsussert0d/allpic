package com.ewsie.allpic.config;

import com.ewsie.allpic.user.role.Role;
import com.ewsie.allpic.user.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initializeRoles();
    }

    private void initializeRoles() {
        Role userRole = new Role();
        userRole.setRoleName("USER");
        Role modRole = new Role();
        modRole.setRoleName("MOD");
        Role adminRole = new Role();
        adminRole.setRoleName("ADMIN");

        roleRepository.save(userRole);
        roleRepository.save(modRole);
        roleRepository.save(adminRole);
    }
}
