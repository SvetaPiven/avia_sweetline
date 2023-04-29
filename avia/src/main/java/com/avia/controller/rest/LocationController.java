package com.avia.controller.rest;

import com.avia.model.entity.Airport;
import com.avia.model.entity.Flight;
import com.avia.model.entity.Ticket;
import com.avia.repository.AirportRepository;
import com.avia.repository.FlightRepository;
import com.avia.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/passengers/miles")
public class LocationController {

    private final FlightRepository flightRepository;

    private final AirportRepository airportRepository;

    private final TicketRepository ticketRepository;

    @GetMapping("/{passengerId}")
    public ResponseEntity<Double> calculatePassengerMiles(@PathVariable Long passengerId) {
        List<Ticket> tickets = ticketRepository.findTicketByIdPass(passengerId);
        double passengerMiles = 0;
        for (Ticket ticket : tickets) {
            Flight flight = flightRepository.findById(ticket.getIdFlight())
                    .orElseThrow(() -> new IllegalArgumentException("Flight not found"));
            Airport departureAirport = airportRepository.findById(flight.getIdDepartureAirport())
                    .orElseThrow(() -> new IllegalArgumentException("Departure airport not found"));
            Airport arrivalAirport = airportRepository.findById(flight.getIdArrivalAirport())
                    .orElseThrow(() -> new IllegalArgumentException("Arrival airport not found"));
            double distance = calculateDistance(departureAirport.getLatitude(), departureAirport.getLongitude(),
                    arrivalAirport.getLatitude(), arrivalAirport.getLongitude());
            passengerMiles += distance;
        }
        return ResponseEntity.ok(passengerMiles);
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;
        return distance * 0.621371;
    }
}