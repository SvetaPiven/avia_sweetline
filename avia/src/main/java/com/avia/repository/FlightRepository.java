package com.avia.repository;

import com.avia.model.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findFlightByIdArrivalAirport(Long id);
    Flight findFlightByFlightNumber(String flightNumber);
    List<Flight> findFlightsByDepartureTimeAfter(Timestamp curDataTime);
}