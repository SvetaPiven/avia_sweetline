package com.avia.service.impl;

import com.avia.dto.TicketCreateDto;
import com.avia.dto.TicketUpdateDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.TicketCreateMapper;
import com.avia.mapper.TicketUpdateMapper;
import com.avia.model.entity.Airline;
import com.avia.model.entity.Flight;
import com.avia.model.entity.Passenger;
import com.avia.model.entity.Ticket;
import com.avia.model.entity.TicketClass;
import com.avia.model.entity.TicketStatus;
import com.avia.repository.AirlineRepository;
import com.avia.repository.FlightRepository;
import com.avia.repository.PassengerRepository;
import com.avia.repository.TicketClassRepository;
import com.avia.repository.TicketRepository;
import com.avia.repository.TicketStatusRepository;
import com.avia.service.TicketService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final PassengerRepository passengerRepository;
    private final TicketRepository ticketRepository;
    private final TicketCreateMapper ticketCreateMapper;
    private final TicketUpdateMapper ticketUpdateMapper;
    private final FlightRepository flightRepository;
    private final TicketClassRepository ticketClassRepository;
    private final AirlineRepository airlineRepository;
    private final TicketStatusRepository ticketStatusRepository;

    @Override
    @Transactional
    public Ticket createTicket(TicketCreateDto ticketCreateDto) {
        Passenger passenger = passengerRepository.findById(ticketCreateDto.getIdPass().getIdPass()).orElseThrow(() ->
                new EntityNotFoundException("Passenger with id " + ticketCreateDto.getIdPass().getIdPass() + " not found"));
        Ticket ticket = ticketCreateMapper.toEntity(ticketCreateDto);
        //ticket.getIdPass().setIdPass(passenger.getIdPass());
        ticket.setIdPass(passenger);

        TicketClass ticketClass = ticketClassRepository.findById(ticketCreateDto.getIdTicketClass().getIdTicketClass()).orElseThrow(() ->
                new EntityNotFoundException("Ticket class with id " + ticketCreateDto.getIdTicketClass().getIdTicketClass() + " not found"));
        //ticket.getIdTicketClass().setIdTicketClass(ticketClass.getIdTicketClass());
        ticket.setIdTicketClass(ticketClass);

        TicketStatus ticketStatus = ticketStatusRepository.findById(6).orElseThrow(() ->
                new EntityNotFoundException("Ticket status class with id " + ticketCreateDto.getIdTicketStatus().getIdTicketStatus() + " not found"));
        //ticket.getIdTicketStatus().setIdTicketStatus(ticketCreateDto.getIdTicketStatus().getIdTicketStatus());
        ticket.setIdTicketStatus(ticketStatus);

        Flight flight = flightRepository.findById(ticketCreateDto.getIdFlight().getIdFlight()).orElseThrow(() ->
                new EntityNotFoundException("Flight with id " + ticketCreateDto.getIdFlight().getIdFlight() + " not found"));
        //ticket.getIdFlight().setIdFlight(flight.getIdFlight());
        ticket.setIdFlight(flight);

        Airline airline = airlineRepository.findById(ticketCreateDto.getIdAirline().getIdAirline()).orElseThrow(() ->
                new EntityNotFoundException("Airline with id " + ticketCreateDto.getIdAirline().getIdAirline() + " not found"));
        //ticket.getIdAirline().setIdAirline(airline.getIdAirline());
        ticket.setIdAirline(airline);

        return ticketRepository.save(ticket);
    }

    @Override
    @Transactional
    public Ticket updateTicket(Long id, TicketUpdateDto ticketUpdateDto) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Ticket with id " + id + " not found"));
        ticket.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        ticketUpdateMapper.partialUpdate(ticketUpdateDto, ticket);
        return ticketRepository.save(ticket);
    }
}
