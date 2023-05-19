package com.avia.repository;

import com.avia.model.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AirportRepository extends JpaRepository<Airport, Long> {

    @Query("SELECT a FROM Airport a JOIN a.flightsDeparture f " +
            "GROUP BY a.idAirport, a.nameAirport, a.city, a.country " +
            "ORDER BY COUNT(f) DESC")
    List<Airport> findPopularAirports();
}