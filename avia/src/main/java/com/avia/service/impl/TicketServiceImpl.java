package com.avia.service.impl;

import com.avia.aspect.MethodExecutionCountAspect;
import com.avia.controller.rest.LocationRestController;
import com.avia.model.dto.TicketClassDto;
import com.avia.model.dto.TicketDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.TicketMapper;
import com.avia.model.entity.Airline;
import com.avia.model.entity.AuthenticationInfo;
import com.avia.model.entity.Flight;
import com.avia.model.entity.Passenger;
import com.avia.model.entity.Ticket;
import com.avia.model.entity.TicketClass;
import com.avia.repository.AirlineRepository;
import com.avia.repository.AirportRepository;
import com.avia.repository.FlightRepository;
import com.avia.repository.PassengerRepository;
import com.avia.repository.TicketClassRepository;
import com.avia.repository.TicketRepository;
import com.avia.repository.UserRepository;
import com.avia.service.AirportService;
import com.avia.service.EmailService;
import com.avia.service.TicketClassService;
import com.avia.service.TicketService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final PassengerRepository passengerRepository;
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final FlightRepository flightRepository;
    private final TicketClassRepository ticketClassRepository;
    private final AirlineRepository airlineRepository;
    private final AirportService airportService;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final LocationRestController locationRestController;
    private static final Logger log = Logger.getLogger(TicketServiceImpl.class);

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

        ticket.setPrice(BigDecimal.valueOf(locationRestController.calculateDistance(flight.getIdDepartureAirport().getLatitude(),
                flight.getIdDepartureAirport().getLongitude(), flight.getIdArrivalAirport().getLatitude(),
                flight.getIdArrivalAirport().getLongitude()) * 0.3).setScale(2, RoundingMode.HALF_UP));

        try {
            emailService.sendSimpleEmail(userRepository.findByIdPass(ticketDto.getIdPass()).getAuthenticationInfo().getEmail(),
                    "Congrats from SweetLine Avia! Your created the ticket!",
                    "Dear customer, here an info from your ticket:\n" +
                            "Ticket class: " + ticketClass.getNameClass() + "\n" +
                            "Departure: " + airportService.getAddressFromLatLng(flight.getIdDepartureAirport().getLatitude(),
                            flight.getIdDepartureAirport().getLongitude()) + "\n" +
                            "Arrival: " + airportService.getAddressFromLatLng(flight.getIdArrivalAirport().getLatitude(),
                            flight.getIdArrivalAirport().getLongitude()) + "\n" +
                            "Flight: " + flight.getFlightNumber() + "\n" +
                            "Airline company: " + airline.getNameAirline() + "\n" +
                            "Departure time: " + flight.getDepartureTime() + "\n" +
                            "Price is: " + ticket.getPrice() + "$\n" +
                            "You are welcome!\n" +
                            "              _\n" +
                            "           -=\\`\\\n" +
                            "      |\\ ____\\_\\__\n" +
                            "   -=\\c`\"\"\"\"\"\"\" \"`)\n" +
                            "      `~~~~~/ /~~`\n" +
                            "           -==/ /\n" +
                            "               '-'" );
        } catch (MailException mailException) {
            log.error("Error while sending out email..{}");
        } catch (Exception e) {
            throw new RuntimeException("No results found");
        }

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
}
