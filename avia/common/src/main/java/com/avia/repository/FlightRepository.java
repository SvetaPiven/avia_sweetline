package com.avia.repository;

import com.avia.model.entity.Airport;
import com.avia.model.entity.Flight;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findFlightByIdArrivalAirport(@NotNull Airport idArrivalAirport);

    Flight findFlightByFlightNumber(String flightNumber);

    List<Flight> findFlightsByDepartureTimeAfter(Timestamp curDataTime);

}