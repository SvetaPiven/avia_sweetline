package com.avia.service;

import com.avia.model.entity.User;
import com.avia.model.dto.UserDto;

public interface UserService {
    User createUser(UserDto userDto);

    User updateUser(Long id, UserDto userDto);

}
