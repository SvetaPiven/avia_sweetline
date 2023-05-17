package com.avia.repository;

import com.avia.model.entity.Airline;
import com.avia.model.entity.FlightStatus;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Cacheable("c_flight_status")
public interface FlightStatusRepository extends JpaRepository<FlightStatus, Integer> {

    @NotNull
    List<FlightStatus> findAll();
}