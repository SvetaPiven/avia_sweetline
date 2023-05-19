package com.avia.service;

import com.avia.model.request.RoleRequest;
import com.avia.model.entity.Role;

public interface RoleService {
    Role createRole(RoleRequest roleRequest);

    Role updateRole(Integer id, RoleRequest roleRequest);
}
