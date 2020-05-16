package com.ewsie.allpic.user.role.service;

import com.ewsie.allpic.user.role.RoleDTO;

public interface RoleDTOService {
    RoleDTO findById(Long id);

    RoleDTO findByRoleName(String roleName);
}
