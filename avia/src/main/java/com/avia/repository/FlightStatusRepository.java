package com.avia.repository;

import com.avia.model.entity.FlightStatus;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Cacheable("c_flight_status")
public interface FlightStatusRepository extends JpaRepository<FlightStatus, Integer> {
}