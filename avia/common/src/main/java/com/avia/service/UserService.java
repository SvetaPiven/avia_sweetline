package com.avia.service;

import com.avia.model.entity.User;
import com.avia.model.request.UserRequest;

public interface UserService {
    User createUser(UserRequest userRequest);

    User updateUser(Long id, UserRequest userRequest);

}
