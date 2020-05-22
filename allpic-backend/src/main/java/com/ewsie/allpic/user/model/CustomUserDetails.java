package com.ewsie.allpic.user.model;

import com.ewsie.allpic.user.role.RoleDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    @Getter
    private final UserDTO user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        RoleDTO role = user.getRole();
        Set<GrantedAuthority> rolesSet = new HashSet<>();
        rolesSet.add(new SimpleGrantedAuthority(role.getRole()));
        return rolesSet;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getIsActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getIsActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getIsActive();
    }

    @Override
    public boolean isEnabled() {
        return user.getIsActive();
    }
}
