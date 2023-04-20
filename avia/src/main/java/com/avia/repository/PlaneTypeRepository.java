package com.avia.repository;

import com.avia.model.entity.PlaneType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PlaneTypeRepository extends JpaRepository<PlaneType, Integer> {
}