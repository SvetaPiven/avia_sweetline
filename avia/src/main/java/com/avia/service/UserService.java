package com.avia.service;

import com.avia.model.dto.UserDto;
import com.avia.model.entity.User;

public interface UserService {
    User createUser(UserDto userDto);

    User updateUser(Long id, UserDto userDto);

}
