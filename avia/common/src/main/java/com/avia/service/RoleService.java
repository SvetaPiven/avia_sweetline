package com.avia.service;

import com.avia.model.entity.Role;
import com.avia.model.request.RoleRequest;

public interface RoleService {
    Role createRole(RoleRequest roleRequest);

    Role updateRole(Integer id, RoleRequest roleRequest);
}
