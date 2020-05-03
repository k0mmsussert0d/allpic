package com.ewsie.allpic.user.role.service;

import com.ewsie.allpic.user.role.Role;

public interface RoleService {
    Role findById(Long id);

    Role findByRoleName(String roleName);
}
