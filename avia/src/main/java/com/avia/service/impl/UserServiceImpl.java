package com.avia.service.impl;

import com.avia.model.dto.UserDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.UserMapper;
import com.avia.model.entity.Role;
import com.avia.model.entity.User;
import com.avia.repository.RoleRepository;
import com.avia.repository.UserRepository;
import com.avia.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public User createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);

        Role defaultRole = roleRepository.findById(1).orElseThrow(() ->
                new EntityNotFoundException("Default Role with id " + 1 + " not found"));
        user.getRoles().add(defaultRole);

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

    @Override
    @Transactional
    public List<Role> getUserAuthorities(Long idUser) {
        return userRepository.getAuthorities(idUser);
    }
}
