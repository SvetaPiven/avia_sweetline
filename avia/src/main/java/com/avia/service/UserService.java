package com.avia.service;

import com.avia.model.dto.UserDto;
import com.avia.model.entity.Role;
import com.avia.model.entity.User;

import java.util.List;

public interface UserService {
    User createUser(UserDto userDto);

    User updateUser(Long id, UserDto userDto);

    List<Role> getUserAuthorities(Long idUser);

}
