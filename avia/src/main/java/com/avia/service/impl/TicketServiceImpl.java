package com.avia.service.impl;

import com.avia.model.entity.requests.TicketDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.TicketMapper;
import com.avia.model.entity.Airline;
import com.avia.model.entity.Flight;
import com.avia.model.entity.Passenger;
import com.avia.model.entity.Ticket;
import com.avia.model.entity.TicketClass;
import com.avia.repository.AirlineRepository;
import com.avia.repository.FlightRepository;
import com.avia.repository.PassengerRepository;
import com.avia.repository.TicketClassRepository;
import com.avia.repository.TicketRepository;
import com.avia.service.TicketService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final PassengerRepository passengerRepository;
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final FlightRepository flightRepository;
    private final TicketClassRepository ticketClassRepository;
    private final AirlineRepository airlineRepository;

    @Override
    @Transactional
    public Ticket createTicket(TicketDto ticketDto) {
        Passenger passenger = passengerRepository.findById(ticketDto.getIdPass()).orElseThrow(() ->
                new EntityNotFoundException("Passenger with id " + ticketDto.getIdPass() + " not found"));
        Ticket ticket = ticketMapper.toEntity(ticketDto);
        ticket.getIdPass().setIdPass(passenger.getIdPass());
        ticket.setIdPass(passenger);

        TicketClass ticketClass = ticketClassRepository.findById(ticketDto.getIdTicketClass()).orElseThrow(() ->
                new EntityNotFoundException("Ticket class with id " + ticketDto.getIdTicketClass() + " not found"));
        ticket.getIdTicketClass().setIdTicketClass(ticketClass.getIdTicketClass());
        ticket.setIdTicketClass(ticketClass);

        Flight flight = flightRepository.findById(ticketDto.getIdFlight()).orElseThrow(() ->
                new EntityNotFoundException("Flight with id " + ticketDto.getIdFlight() + " not found"));
        ticket.getIdFlight().setIdFlight(flight.getIdFlight());
        ticket.setIdFlight(flight);

        Airline airline = airlineRepository.findById(ticketDto.getIdAirline()).orElseThrow(() ->
                new EntityNotFoundException("Airline with id " + ticketDto.getIdAirline() + " not found"));
        ticket.getIdAirline().setIdAirline(airline.getIdAirline());
        ticket.setIdAirline(airline);

        return ticketRepository.save(ticket);
    }

    @Override
    @Transactional
    public Ticket updateTicket(Long id, TicketDto ticketDto) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Ticket with id " + id + " not found"));
        ticketMapper.partialUpdate(ticketDto, ticket);
        return ticketRepository.save(ticket);
    }

//    @Override
//    @Transactional
//    public Ticket createTicket(TicketCreateDto ticketCreateDto) {
//        Passenger passenger = passengerRepository.findById(ticketCreateDto.getIdPass()).orElseThrow(() ->
//                new EntityNotFoundException("Passenger with id " + ticketCreateDto.getIdPass() + " not found"));
//
//        // Check if passenger has a ticket for another flight during the same time period
//        boolean ticketExists = ticketRepository.existsByPassengerAndFlightIdAndDepartureTimeBetween(
//                passenger, ticketCreateDto.getIdFlight(), ticketCreateDto.getDepartureTime().minusDays(1), ticketCreateDto.getDepartureTime().plusDays(1));
//        if (ticketExists) {
//            throw new IllegalStateException("Passenger already has a ticket for another flight during the same time period");
//        }
//
//            Ticket ticket = ticketCreateMapper.toEntity(ticketCreateDto);
//            ticket.getIdPass().setIdPass(passenger.getIdPass());
//            ticket.setIdPass(passenger);
//
//            TicketClass ticketClass = ticketClassRepository.findById(ticketCreateDto.getIdTicketClass()).orElseThrow(() ->
//                    new EntityNotFoundException("Ticket class with id " + ticketCreateDto.getIdTicketClass() + " not found"));
//            ticket.getIdTicketClass().setIdTicketClass(ticketClass.getIdTicketClass());
//            ticket.setIdTicketClass(ticketClass);
//
//            Flight flight = flightRepository.findById(ticketCreateDto.getIdFlight()).orElseThrow(() ->
//                    new EntityNotFoundException("Flight with id " + ticketCreateDto.getIdFlight() + " not found"));
//            ticket.getIdFlight().setIdFlight(flight.getIdFlight());
//            ticket.setIdFlight(flight);
//
//            Airline airline = airlineRepository.findById(ticketCreateDto.getIdAirline()).orElseThrow(() ->
//                    new EntityNotFoundException("Airline with id " + ticketCreateDto.getIdAirline() + " not found"));
//            ticket.getIdAirline().setIdAirline(airline.getIdAirline());
//            ticket.setIdAirline(airline);
//
//            return ticketRepository.save(ticket);
//        }
//
//        // Calculate ticket price based on flight distance
//        double distance = calculateDistance(flight.getIdDepartureAirport(), flight.getIdArrivalAirport());
//        double price = distance * ticketClass.getPricePerKm();
//        ticket.setPrice(price);
//
//        // Check if the ticket is being purchased at least one day before the flight
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime departureTime = ticketCreateDto.getDepartureTime().toLocalDateTime();
//        if (now.isAfter(departureTime.minusDays(1))) {
//            throw new IllegalStateException("Ticket must be purchased at least one day before the flight");
//        }
//
//        return ticketRepository.save(ticket);
//    }
//
//    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
//        double earthRadius = 6371;
//        double dLat = Math.toRadians(lat2 - lat1);
//        double dLon = Math.toRadians(lon2 - lon1);
//        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
//                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
//                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        double distance = earthRadius * c;
//        return distance * 0.621371;
//    }
}
