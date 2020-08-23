package com.ewsie.allpic.user.mod.controller.impl;

import com.ewsie.allpic.user.mod.controller.ModeratorController;
import com.ewsie.allpic.user.model.UserDTO;
import com.ewsie.allpic.user.role.RoleDTO;
import com.ewsie.allpic.user.role.service.RoleDTOService;
import com.ewsie.allpic.user.service.UserDTOService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModeratorControllerImpl implements ModeratorController {

    private final RoleDTOService roleDTOService;
    private final UserDTOService userDTOService;

    @Override
    public ResponseEntity<List<UserDTO>> getListOfMods() {
        RoleDTO modRole = getModRole();
        List<UserDTO> mods = userDTOService.findUsersByRole(modRole);

        if (mods == null || mods.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }

        return ResponseEntity.status(HttpStatus.OK).body(mods);
    }

    @Override
    public ResponseEntity<UserDTO> addMod(Long userId) {
        RoleDTO modRole = getModRole();
        RoleDTO adminRole = getAdminRole();

        Optional<UserDTO> user = Optional.ofNullable(userDTOService.findById(userId));

        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID " + userId + " does not exists");
        } else if (user.get().getRole().equals(modRole) || user.get().getRole().equals(adminRole)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with ID " + userId + " is already a mod or an admin");
        }

        user.get().setRole(modRole);
        userDTOService.create(user.get());

        return ResponseEntity.status(HttpStatus.OK).body(user.get());
    }

    @Override
    public ResponseEntity<UserDTO> removeMod(Long userId) {
        RoleDTO userRole = getUserRole();
        RoleDTO modRole = getModRole();
        RoleDTO adminRole = getAdminRole();

        Optional<UserDTO> user = Optional.ofNullable(userDTOService.findById(userId));

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else if (!user.get().getRole().equals(modRole)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        user.get().setRole(userRole);
        userDTOService.create(user.get());

        return ResponseEntity.status(HttpStatus.OK).body(user.get());
    }

    private RoleDTO getUserRole() {
        return roleDTOService.findByRoleName("USER");
    }

    private RoleDTO getModRole() {
        return roleDTOService.findByRoleName("MOD");
    }

    private RoleDTO getAdminRole() {
        return roleDTOService.findByRoleName("ADMIN");
    }
}
