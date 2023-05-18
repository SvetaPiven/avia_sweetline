package com.avia.repository;

import com.avia.model.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AirportRepository extends JpaRepository<Airport, Long> {
    @Query("SELECT a.idAirport, a.nameAirport, a.city, a.country, COUNT(f.idFlight) AS countFlights " +
            "FROM Flight f JOIN f.idDepartureAirport a " +
            "GROUP BY a.idAirport, a.nameAirport, a.city, a.country  " +
            "ORDER BY countFlights DESC")
    List<Object []> findPopularAirports();
}