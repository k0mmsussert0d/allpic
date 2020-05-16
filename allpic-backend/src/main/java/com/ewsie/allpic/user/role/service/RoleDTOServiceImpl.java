package com.ewsie.allpic.user.role.service;

import com.ewsie.allpic.user.role.Role;
import com.ewsie.allpic.user.role.RoleDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleDTOServiceImpl implements RoleDTOService {

    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @Override
    public RoleDTO findById(Long id) {
        Optional<Role> role = Optional.ofNullable(roleService.findById(id));

        if (role.isPresent()) {
            return modelMapper.map(role.get(), RoleDTO.class);
        }

        return null;
    }

    @Override
    public RoleDTO findByRoleName(String roleName) {
        Optional<Role> role = Optional.ofNullable(roleService.findByRoleName(roleName));

        if (role.isPresent()) {
            return modelMapper.map(role.get(), RoleDTO.class);
        }

        return null;
    }
}
