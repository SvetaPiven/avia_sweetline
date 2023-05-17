package com.avia.repository;

import com.avia.model.entity.Airline;
import com.avia.model.entity.PlaneType;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Cacheable("c_plane_types")
public interface PlaneTypeRepository extends JpaRepository<PlaneType, Integer> {

    @NotNull
    List<PlaneType> findAll();
}