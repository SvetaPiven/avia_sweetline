package com.avia.repository;

import com.avia.model.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
}