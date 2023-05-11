package com.avia.repository;

import com.avia.model.entity.PlaneType;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

@Cacheable("c_plane_types")
public interface PlaneTypeRepository extends JpaRepository<PlaneType, Integer> {
}