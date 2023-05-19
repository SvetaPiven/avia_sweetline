package com.avia.controller.rest;

import com.avia.model.entity.Airport;
import com.avia.model.entity.Flight;
import com.avia.model.entity.Passenger;
import com.avia.model.entity.Ticket;
import com.avia.repository.AirportRepository;
import com.avia.repository.FlightRepository;
import com.avia.repository.PassengerRepository;
import com.avia.repository.TicketRepository;
import com.avia.service.AirportService;
import com.avia.service.FlightService;
import com.avia.util.CalculateDistance;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/passengers/miles")
public class LocationRestController {

    private final FlightService flightService;

    private final AirportService airportService;

    private final TicketRepository ticketRepository;

    private final PassengerRepository passengerRepository;

    private final CalculateDistance calculateDistance;

    @PutMapping("/calculate")
    public ResponseEntity<String> calculatePassengerMiles() {
        List<Passenger> passengers = passengerRepository.findAll();
        for (Passenger passenger : passengers) {
            List<Ticket> tickets = ticketRepository.findTicketByIdPass(passenger);
            double passengerMiles = 0;
            for (Ticket ticket : tickets) {
                if (Objects.equals(ticket.getIdTicketStatus().getNameTicketStatus(), "Used")) {

                    Flight flight = flightService.findById(ticket.getIdFlight().getIdFlight());

                    Airport departureAirport = airportService.findById(flight.getIdDepartureAirport().getIdAirport());

                    Airport arrivalAirport = airportService.findById(flight.getIdArrivalAirport().getIdAirport());

                    double latitudeDeparture = departureAirport.getLatitude();
                    double latitudeArrival = arrivalAirport.getLatitude();
                    double longitudeDeparture = departureAirport.getLongitude();
                    double longitudeArrival = arrivalAirport.getLongitude();

                    double distance = calculateDistance.calculate(latitudeDeparture, longitudeDeparture,
                            latitudeArrival, longitudeArrival);

                    if (Objects.equals(flight.getIdFlightStatus().getNameFlightStatus(), "Arrived")) {
                        if (Objects.equals(ticket.getIdTicketClass().getNameClass(), "Business class")) {
                            passengerMiles += distance * 1.5;
                        } else {
                            passengerMiles += distance;
                        }
                        BigDecimal ticketPrice = ticket.getPrice();
                        BigDecimal discount = BigDecimal.valueOf(passengerMiles / 100);
                        ticketPrice = ticketPrice.subtract(discount);
                        ticket.setPrice(ticketPrice);
                        ticketRepository.save(ticket);
                    }
                }
            }
            passenger.setMiles(passengerMiles);
            passengerRepository.save(passenger);
        }
        return ResponseEntity.ok("Miles calculated for all passengers.");
    }
}