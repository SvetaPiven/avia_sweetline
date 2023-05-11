package com.avia.repository;

import com.avia.model.entity.Role;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

@Cacheable("c_role")
public interface RoleRepository extends JpaRepository<Role, Integer> {
}