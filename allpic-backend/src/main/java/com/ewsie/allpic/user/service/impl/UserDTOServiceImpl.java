package com.ewsie.allpic.user.service.impl;

import com.ewsie.allpic.user.model.User;
import com.ewsie.allpic.user.model.UserDTO;
import com.ewsie.allpic.user.role.Role;
import com.ewsie.allpic.user.role.RoleDTO;
import com.ewsie.allpic.user.service.UserDTOService;
import com.ewsie.allpic.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserDTOServiceImpl implements UserDTOService {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public void create(UserDTO userDTO) {
        userService.create(modelMapper.map(userDTO, User.class));
    }

    @Override
    public List<UserDTO> getAll() {

        List<User> users = userService.getAll();

        if (users.isEmpty()) {
            return null;
        }

        Type listType = new TypeToken<List<UserDTO>>(){}.getType();
        return modelMapper.map(users, listType);
    }

    @Override
    public UserDTO findById(Long id) {
        Optional<User> user = Optional.ofNullable(userService.findById(id));

        return user.map(value -> modelMapper.map(value, UserDTO.class)).orElse(null);
    }

    @Override
    public UserDTO findByUsername(String username) {
        Optional<User> user = Optional.ofNullable(userService.findByUsername(username));

        return user.map(value -> modelMapper.map(value, UserDTO.class)).orElse(null);
    }

    @Override
    public UserDTO findByEmail(String email) {
        Optional<User> user = Optional.ofNullable(userService.findByEmail(email));

        return user.map(value -> modelMapper.map(value, UserDTO.class)).orElse(null);
    }

    @Override
    public List<UserDTO> findUsersByRole(RoleDTO role) {
        Optional<List<User>> users = Optional.ofNullable(userService.findUsersByRole(modelMapper.map(role, Role.class)));
        if (users.isEmpty()) {
            return null;
        }

        Type listType = new TypeToken<List<UserDTO>>(){}.getType();

        return modelMapper.map(users.get(), listType);
    }
}
