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
import com.avia.util.FlightCoordinatesTaker;
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

    private final FlightCoordinatesTaker flightCoordinatesTaker;

    @Override
    public void calculatePassengerMiles(Passenger passenger) {
        List<Ticket> tickets = ticketRepository.findTicketByPassenger(passenger);
        double passengerMiles = 0;
        for (Ticket ticket : tickets) {
            if (Objects.equals(ticket.getTicketStatus().getNameTicketStatus(), "Used")) {

                Flight flight = flightService.findById(ticket.getFlight().getIdFlight());

                double distance = calculateDistance.calculate(flightCoordinatesTaker.flightCoordinatesTaker(flight));

                if (Objects.equals(flight.getFlightStatus().getNameFlightStatus(), "Arrived")) {
                    if (Objects.equals(ticket.getTicketClass().getNameClass(), "Business class")) {
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