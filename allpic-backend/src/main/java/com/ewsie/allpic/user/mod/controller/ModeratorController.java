package com.ewsie.allpic.user.mod.controller;

import com.ewsie.allpic.user.model.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/mod")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public interface ModeratorController {

    @GetMapping("/")
    ResponseEntity<List<UserDTO>> getListOfMods();

    @PutMapping("/{userId}")
    ResponseEntity<Void> addMod(@PathVariable Long userId);

    @DeleteMapping("/{userId}")
    ResponseEntity<Void> removeMod(@PathVariable Long userId);
}
