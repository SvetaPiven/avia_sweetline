package com.avia.repository;

import com.avia.model.entity.FlightStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface FlightStatusRepository extends JpaRepository<FlightStatus, Integer> {
}