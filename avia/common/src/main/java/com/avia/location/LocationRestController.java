package com.avia.location;

import com.avia.model.entity.Airport;
import com.avia.model.entity.Flight;
import com.avia.model.entity.Passenger;
import com.avia.model.entity.Ticket;
import com.avia.repository.AirportRepository;
import com.avia.repository.FlightRepository;
import com.avia.repository.PassengerRepository;
import com.avia.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/passengers/miles")
public class LocationRestController {

    private final FlightRepository flightRepository;

    private final AirportRepository airportRepository;

    private final TicketRepository ticketRepository;

    private final PassengerRepository passengerRepository;

    @GetMapping("/calculate")
    public ResponseEntity<String> calculatePassengerMiles() {
        List<Passenger> passengers = passengerRepository.findAll();
        for (Passenger passenger : passengers) {
            List<Ticket> tickets = ticketRepository.findTicketByIdPass(passenger);
            double passengerMiles = 0;
            for (Ticket ticket : tickets) {
                if (ticket.getIdTicketStatus().getIdTicketStatus() == 5) {
                    Flight flight = flightRepository.findById(ticket.getIdFlight().getIdFlight())
                            .orElseThrow(() -> new IllegalArgumentException("Flight not found"));
                    Airport departureAirport = airportRepository.findById(flight.getIdDepartureAirport().getIdAirport())
                            .orElseThrow(() -> new IllegalArgumentException("Departure airport not found"));
                    Airport arrivalAirport = airportRepository.findById(flight.getIdArrivalAirport().getIdAirport())
                            .orElseThrow(() -> new IllegalArgumentException("Arrival airport not found"));
                    double distance = calculateDistance(departureAirport.getLatitude(), departureAirport.getLongitude(),
                            arrivalAirport.getLatitude(), arrivalAirport.getLongitude());

                    if (flight.getIdFlightStatus().getIdFlightStatus() == 4) {
                        if (ticket.getIdTicketClass().getIdTicketClass() == 2) {
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

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
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