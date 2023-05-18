package com.avia.service;

import com.avia.model.dto.RoleDto;
import com.avia.model.entity.Role;

public interface RoleService {
    Role createRole(RoleDto roleDto);

    Role updateRole(Integer id, RoleDto roleDto);
}
