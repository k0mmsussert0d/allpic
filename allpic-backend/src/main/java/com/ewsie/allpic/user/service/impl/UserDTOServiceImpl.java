package com.ewsie.allpic.user.service.impl;

import com.ewsie.allpic.user.model.User;
import com.ewsie.allpic.user.model.UserDTO;
import com.ewsie.allpic.user.service.UserDTOService;
import com.ewsie.allpic.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
    public UserDTO findById(Long id) {
        Optional<User> user = Optional.ofNullable(userService.findById(id));

        return user.isPresent() ? modelMapper.map(user, UserDTO.class) : null;
    }

    @Override
    public UserDTO findByUsername(String username) {
        Optional<User> user = Optional.ofNullable(userService.findByUsername(username));

        return user.isPresent() ? modelMapper.map(user.get(), UserDTO.class) : null;
    }

    @Override
    public UserDTO findByEmail(String email) {
        Optional<User> user = Optional.ofNullable(userService.findByEmail(email));

        return user.isPresent() ? modelMapper.map(user, UserDTO.class) : null;
    }
}
