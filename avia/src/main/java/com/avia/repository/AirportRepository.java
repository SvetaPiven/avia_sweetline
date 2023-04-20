package com.avia.repository;

import com.avia.model.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AirportRepository extends JpaRepository<Airport, Long> {
}