package com.avia.service.impl;

import com.avia.model.entity.User;
import com.avia.model.request.UserRequest;
import com.avia.repository.UserRepository;
import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.UserMapper;
import com.avia.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    @Transactional
    public User createUser(UserRequest userRequest) {

        User user = userMapper.toEntity(userRequest);

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
