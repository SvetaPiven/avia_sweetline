package com.avia.repository;

import com.avia.model.entity.Flight;
import com.avia.model.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findFlightByIdArrivalAirport(Long id);
    Flight findFlightByFlightNumber(String flightNumber);
    List<Flight> findFlightsByDepartureTimeAfter(Timestamp curDataTime);

//    @Query(value = "select p.id_pass from passengers as p inner join tickets t on p.id_pass = t.id_pass" +
//            "inner join ", nativeQuery = true)
//
//    List<Flight> findByPassenger(Passenger passenger);
}