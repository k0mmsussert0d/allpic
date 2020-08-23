package com.ewsie.allpic.user.mod.controller;

import com.ewsie.allpic.user.model.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags={"Mods"})
@RequestMapping("/mod")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public interface ModeratorController {

    @ApiOperation(value = "Gets list of currently active mods")
    @GetMapping("/")
    ResponseEntity<List<UserDTO>> getListOfMods();

    @ApiOperation(value = "Gives mods rights to the user")
    @PostMapping("/{userId}")
    ResponseEntity<UserDTO> addMod(@PathVariable Long userId);

    @ApiOperation(value = "Revokes mods rights to the user")
    @DeleteMapping("/{userId}")
    ResponseEntity<UserDTO> removeMod(@PathVariable Long userId);
}
