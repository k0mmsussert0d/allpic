package com.ewsie.allpic.config;

import com.ewsie.allpic.user.role.Role;
import com.ewsie.allpic.user.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(DataLoader.class);

    private final RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) {
        initializeRoles();
    }

    private void initializeRoles() {
        try {
            Role userRole = new Role();
            userRole.setRoleName("USER");
            Role modRole = new Role();
            modRole.setRoleName("MOD");
            Role adminRole = new Role();
            adminRole.setRoleName("ADMIN");

            roleRepository.save(userRole);
            roleRepository.save(modRole);
            roleRepository.save(adminRole);
        } catch (DataIntegrityViolationException e) {
            LOGGER.warn("Initialization sequence has not been properly executed");
        }
    }
}
