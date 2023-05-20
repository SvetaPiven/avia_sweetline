package com.avia.service.impl;

import com.avia.model.entity.User;
import com.avia.model.request.UserRequest;
import com.avia.repository.UserRepository;
import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.UserMapper;
import com.avia.service.UserService;
import com.avia.util.PasswordEncode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncode passwordEncode;

    @Override
    @Transactional
    public User createUser(UserRequest userRequest) {

        User user = userMapper.toEntity(userRequest);

        String encodedPassword = passwordEncode.encodePassword(user.getAuthenticationInfo().getUserPassword());
        user.getAuthenticationInfo().setUserPassword(encodedPassword);

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(Long id, UserRequest userRequest) {

        User user = userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User with id " + id + " not found"));

        userMapper.partialUpdate(userRequest, user);

        return userRepository.save(user);
    }
}
