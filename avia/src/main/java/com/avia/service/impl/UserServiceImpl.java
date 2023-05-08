package com.avia.service.impl;

import com.avia.dto.requests.UserDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.UserMapper;
import com.avia.model.entity.Role;
import com.avia.model.entity.User;
import com.avia.repository.UserRepository;
import com.avia.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public User createUser(UserDto userDto) {
        Role role = Role.builder().idRole(1).build();
        User user = userMapper.toEntity(userDto);
        user.setRoles(Collections.singleton(role));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(Long id, UserDto userDto) {

        User user = userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User with id " + id + " not found"));

        userMapper.partialUpdate(userDto, user);

        return userRepository.save(user);
    }
}
