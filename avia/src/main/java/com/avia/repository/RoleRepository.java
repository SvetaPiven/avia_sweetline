package com.avia.repository;

import com.avia.model.entity.Airline;
import com.avia.model.entity.Role;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Cacheable("c_role")
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @NotNull
    List<Role> findAll();
}