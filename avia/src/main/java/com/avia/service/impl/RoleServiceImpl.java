package com.avia.service.impl;

import com.avia.model.dto.RoleDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.RoleMapper;
import com.avia.model.entity.Role;
import com.avia.repository.RoleRepository;
import com.avia.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    @Transactional
    public Role createRole(RoleDto roleDto) {
        Role role = roleMapper.toEntity(roleDto);
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public Role updateRole(Integer id, RoleDto roleDto) {
        Role role = roleRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Role type with id " + id + " not found"));
        roleMapper.partialUpdate(roleDto, role);
        return roleRepository.save(role);
    }
}
