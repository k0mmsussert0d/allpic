package com.ewsie.allpic.user.service;

import com.ewsie.allpic.user.model.UserDTO;
import com.ewsie.allpic.user.role.RoleDTO;

import java.util.List;

public interface UserDTOService {
    void create(UserDTO userDTO);

    UserDTO findById(Long id);

    UserDTO findByUsername(String username);

    UserDTO findByEmail(String email);

    List<UserDTO> findUsersByRole(RoleDTO role);
}
