package com.ewsie.allpic.user.mod.controller.impl;

import com.ewsie.allpic.user.mod.controller.ModeratorController;
import com.ewsie.allpic.user.model.User;
import com.ewsie.allpic.user.model.UserDTO;
import com.ewsie.allpic.user.role.Role;
import com.ewsie.allpic.user.role.RoleDTO;
import com.ewsie.allpic.user.role.service.RoleDTOService;
import com.ewsie.allpic.user.role.service.RoleService;
import com.ewsie.allpic.user.service.UserDTOService;
import com.ewsie.allpic.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModeratorControllerImpl implements ModeratorController {

    private final ModelMapper modelMapper;
    private final RoleService roleService;
    private final UserService userService;

    @Override
    public ResponseEntity<List<UserDTO>> getListOfMods() {
        Role modRole = getModRole();
        List<User> mods = userService.findUsersByRole(modRole);

        if (mods == null || mods.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }

        Type listType = new TypeToken<List<UserDTO>>(){}.getType();

        return ResponseEntity.ok(modelMapper.map(mods, listType));
    }

    @Override
    public ResponseEntity<Void> addMod(Long userId) {
        Role modRole = getModRole();
        Role adminRole = getAdminRole();

        Optional<User> user = Optional.ofNullable(userService.findById(userId));

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else if (user.get().getRole() == modRole || user.get().getRole() == adminRole) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        user.get().setRole(modRole);
        userService.create(user.get());

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> removeMod(Long userId) {
        Role userRole = getUserRole();
        Role modRole = getModRole();
        Role adminRole = getAdminRole();

        Optional<User> user = Optional.ofNullable(userService.findById(userId));

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else if (user.get().getRole() != modRole || user.get().getRole() == adminRole) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        user.get().setRole(userRole);
        userService.create(user.get());

        return ResponseEntity.ok().build();
    }

    private Role getUserRole() {
        return roleService.findByRoleName("USER");
    }

    private Role getModRole() {
        return roleService.findByRoleName("MOD");
    }

    private Role getAdminRole() {
        return roleService.findByRoleName("ADMIN");
    }
}
