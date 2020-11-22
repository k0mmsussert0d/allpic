package com.ewsie.allpic.user.controller.impl;

import com.ewsie.allpic.user.model.CustomUserDetails;
import com.ewsie.allpic.user.model.UserDTO;
import com.ewsie.allpic.user.role.RoleDTO;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import java.time.LocalDateTime;
import java.util.List;

@TestConfiguration
public class SpringSecurityForUserControllerImplTestConfig {

    @Bean
    public UserDTO testUser() {
        return UserDTO.builder()
                .username("username")
                .email("username@example.com")
                .password("password")
                .registerTime(LocalDateTime.of(2020, 1, 1, 12, 0, 0, 0))
                .isActive(true)
                .role(RoleDTO.builder().roleName("USER").build())
                .build();
    }

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {

        CustomUserDetails userDetails = new CustomUserDetails(testUser());

        return new UserDetailsManager() {

            private final InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(List.of(userDetails));


            @Override
            public void createUser(UserDetails userDetails) {
                this.inMemoryUserDetailsManager.createUser(userDetails);
            }

            @Override
            public void updateUser(UserDetails userDetails) {
                this.inMemoryUserDetailsManager.updateUser(userDetails);
            }

            @Override
            public void deleteUser(String s) {
                this.inMemoryUserDetailsManager.deleteUser(s);
            }

            @Override
            public void changePassword(String s, String s1) {
                this.inMemoryUserDetailsManager.changePassword(s, s1);
            }

            @Override
            public boolean userExists(String s) {
                return this.inMemoryUserDetailsManager.userExists(s);
            }

            @Override
            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
                return new CustomUserDetails(testUser());
            }
        };
    }
}
