package com.avia.service.impl;

import com.avia.model.entity.Airport;
import com.avia.model.entity.Flight;
import com.avia.model.entity.Passenger;
import com.avia.model.entity.Ticket;
import com.avia.repository.PassengerRepository;
import com.avia.repository.TicketRepository;
import com.avia.service.AirportService;
import com.avia.service.FlightService;
import com.avia.service.LocationService;
import com.avia.util.CalculateDistance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class LocationServiceImpl implements LocationService {

    private final FlightService flightService;

    private final AirportService airportService;

    private final TicketRepository ticketRepository;

    private final PassengerRepository passengerRepository;

    private final CalculateDistance calculateDistance;

    @Override
    public void calculatePassengerMiles(Passenger passenger) {
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
}