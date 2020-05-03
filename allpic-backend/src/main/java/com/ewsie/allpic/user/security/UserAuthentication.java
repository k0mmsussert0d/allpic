package com.ewsie.allpic.user.security;

import com.ewsie.allpic.user.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@RequiredArgsConstructor
public class UserAuthentication implements Authentication {

    private final CustomUserDetails customUserDetails;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return customUserDetails.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return customUserDetails;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        throw new UnsupportedOperationException("This authentication object is always authenticated");
    }

    @Override
    public String getName() {
        return customUserDetails.getUsername();
    }
}
